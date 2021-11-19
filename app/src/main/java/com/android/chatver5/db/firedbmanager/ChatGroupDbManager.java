package com.android.chatver5.db.firedbmanager;

import android.app.Application;
import androidx.annotation.NonNull;
import com.android.chatver5.db.data.ChatGroup;
import com.android.chatver5.db.firedb.Db;
import com.android.chatver5.db.roomdb.dao.ChatGroupDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupDbManager implements DbManager<ChatGroup> {

  private final Db<ChatGroup> db;
  private final ChatGroupDao dao;
  Disposable dispUpdate;

  public ChatGroupDbManager(String fullPath, Application application) {
    this.db = new Db<>(fullPath, ChatGroup.class);
    this.dao = AppDatabase.getDatabase(application).chatGroupDao();
  }

  public void getUpdateInRealTime() {
    dispose();
    keepRoomDbConsistentWithFireDb().subscribe(() -> Utilities.log(LogType.d, "done."));
    // 데이터 일관성 체크.
    dispUpdate = keepRoomDbConsistentWithFireDb()
        // 실시간으로 데이터 추적.
        .andThen(db.getUpdateInRealTime())
        .observeOn(Schedulers.io())
        // Room DB 조작.
        .flatMapCompletable(dbChange -> {
          Utilities.log(LogType.d, "change!");
          switch (dbChange.getType()) {
            case ADD:
              return dao.insert(dbChange.getData());
            case MODIFY:
              return dao.update(dbChange.getData());
            case DELETE:
              return dao.delete(dbChange.getData());
            default:
              return Completable
                  .error(new Throwable(Utilities.makeLog("not compatible type.")));
          }
        })
        .subscribe(() -> Utilities.log(LogType.d, "done."),
            err -> Utilities.log(LogType.w, err.getMessage()));
  }

  private Completable keepRoomDbConsistentWithFireDb() {
    // update state 초기화. true -> false.
    return this.dao.resetUpdateState()
        .subscribeOn(Schedulers.io())
        // 서버에서 모든 데이터 가져오기.
        .andThen(db.getAllItem())
        .observeOn(Schedulers.computation())
        // primary key 만 뽑아내기.
        .map(items -> {
          List<String> primaryKeys = new ArrayList<>();
          for (ChatGroup item : items) {
            primaryKeys.add(item.getPrimaryKey());
          }
          return primaryKeys;
        }).observeOn(Schedulers.io())
        // 서버에 있는 데이터만 체크.
        .flatMapCompletable(
            primaryKeys ->
                Completable.defer(() -> this.dao.markUpdatedItems(primaryKeys)))
        // 서버에 없는 데이터 제거.
        .andThen(this.dao.deleteNotUpdated());
  }

  @Override
  public void addItem(@NonNull ChatGroup item) {
    db.runTransaction((transaction) -> {
      db.setData(item, transaction);
      return null;
    });
  }

  @Override
  public void deleteItem(@NonNull ChatGroup item) {
    db.runTransaction(transaction -> {
      db.deleteData(item, transaction);
      return null;
    });
  }

  public void deleteItem(@NonNull String primaryKey) {
    db.runTransaction(transaction -> {
      db.deleteData(primaryKey, transaction);
      return null;
    });
  }

  @Override
  public void modifyItem(@NonNull ChatGroup item) {
    db.runTransaction(transaction -> {
      db.setData(item, transaction);
      return null;
    });
  }

  @Override
  public void dispose() {
    if (dispUpdate != null && !dispUpdate.isDisposed()) {
      dispUpdate.dispose();
    }
  }
}
