package com.android.chatver5.db.dbtracker;

import android.app.Application;
import com.android.chatver5.db.data.Party;
import com.android.chatver5.db.firedb.ServerDb;
import com.android.chatver5.db.roomdb.dao.PartyDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupDbTracker implements DbTracker<Party> {

  private final ServerDb<Party> serverDb;
  private final PartyDao dao;
  Disposable dispUpdate;

  public ChatGroupDbTracker(String fullPath, Application application) {
    this.serverDb = new ServerDb<>(fullPath, Party.class);
    this.dao = AppDatabase.getDatabase(application).chatGroupDao();
  }

  public void getUpdateInRealTime() {
    dispose();
    dispUpdate = keepRoomDbConsistentWithFireDb()
        // 실시간으로 데이터 추적.
        .andThen(serverDb.getUpdateInRealTime())
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
        .andThen(serverDb.getAllItem())
        .observeOn(Schedulers.computation())
        // primary key 만 뽑아내기.
        .map(items -> {
          List<String> primaryKeys = new ArrayList<>();
          for (Party item : items) {
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
  public void dispose() {
    if (dispUpdate != null && !dispUpdate.isDisposed()) {
      dispUpdate.dispose();
    }
  }
}
