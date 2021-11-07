package com.android.chatver4.db.variousdb;

import android.util.Log;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.db.Db;
import com.android.chatver4.db.change.DbChanges;
import com.android.chatver4.db.change.DbChanges.DbChangesType;
import com.android.chatver4.utilities.Utilities;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import io.reactivex.rxjava3.core.Observable;

public class ChatDb extends Db<Chat> {

  private Query postOrder;

  public ChatDb(String fullPath, Class<Chat> dataClass) {
    super(fullPath, dataClass);
    postOrder = cltRef.orderBy("timestamp", Direction.ASCENDING);
  }

  @Override
  public Observable<DbChanges<Chat>> getChangesInRealTime() {
    final String FUNC_TAG = Utilities.makeFuncTag();
    // DbChanges 데이터 스트림 갖는 Observable 리턴.
    return Observable.create(emitter -> {
      // 변경 사항 추적.
      this.lisReg = postOrder.addSnapshotListener((value, error) -> {
        // 에러 핸들링.
        if (error != null || value == null) {
          Log.w(TAG, FUNC_TAG + "\nerror!\n");
          emitter.onError(new Throwable(TAG + FUNC_TAG + "error!\n"));
          return;
        }

        // 데이터 실시간으로 업데이트 받기.
        for (DocumentChange dc : value.getDocumentChanges()) {
          Chat changes = dataClass.cast(dc.getDocument()
              .toObject(dataClass));
          int newIdx = dc.getNewIndex();
          int oldIdx = dc.getOldIndex();
          switch (dc.getType()) {
            case ADDED:
              Log.d(TAG, FUNC_TAG + "get new added info\n" +
                  "data : " + changes + "\n" +
                  "index : " + newIdx + "\n");
              emitter.onNext(new DbChanges<>(-1, newIdx, changes, DbChangesType.ADD));
              break;
            case MODIFIED:
              Log.d(TAG, FUNC_TAG + "get modified info\n" +
                  "data : " + changes + "\n" +
                  "index : " + newIdx + "\n");
              emitter.onNext(new DbChanges<>(oldIdx, newIdx, changes, DbChangesType.MODIFY));
              break;
            case REMOVED:
              Log.d(TAG, FUNC_TAG + "get removed info\n" +
                  "data : " + changes + "\n" +
                  "index : " + oldIdx + "\n");
              emitter.onNext(new DbChanges<>(oldIdx, -1, changes, DbChangesType.DELETE));
              break;
          }
        }
      });
    });
  }
}