package com.android.chatver4.db;

import android.util.Log;
import com.android.chatver4.db.change.DbChanges;
import com.android.chatver4.db.change.DbChanges.DbChangesType;
import com.android.chatver4.db.dbdata.Data;
import com.android.chatver4.utilities.Utilities;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.Transaction.Function;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class Db<T extends Data> {

  protected FirebaseFirestore db;
  protected Class<T> dataClass;
  protected String TAG;
  protected CollectionReference cltRef;
  protected ListenerRegistration lisReg;

  public Db(String fullPath, Class<T> dataClass) {
    this.dataClass = dataClass;
    TAG = Utilities.extractUpper(dataClass.getSimpleName()) + "_Db_R";
    db = FirebaseFirestore.getInstance();
    cltRef = db.collection(fullPath);
  }

  // 실시간으로 업데이트 받는 함수.
  public Observable<DbChanges<T>> getChangesInRealTime() {
    final String FUNC_TAG = Utilities.makeFuncTag();
    // DbChanges 데이터 스트림 갖는 Observable 리턴.
    return Observable.create(emitter -> {
      // 변경 사항 추적.
      this.lisReg = cltRef.addSnapshotListener((value, error) -> {
        // 에러 핸들링.
        if (error != null || value == null) {
          Log.w(TAG, FUNC_TAG + "\nerror!\n");
          emitter.onError(new Throwable(TAG + FUNC_TAG + "error!\n"));
          return;
        }

        // 데이터 실시간으로 업데이트 받기.
        for (DocumentChange dc : value.getDocumentChanges()) {
          T changes = dataClass.cast(dc.getDocument()
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

  // DB 에 정보가 존재함을 확인하는 함수.
  public boolean checkDataExisted(T data, Transaction transaction)
      throws FirebaseFirestoreException {
    return transaction.get(cltRef.document(data.getPrimaryKey())).exists();
  }

  public boolean checkDataEqual(T data, Transaction transaction)
      throws FirebaseFirestoreException {
    return transaction.get(cltRef.document(data.getPrimaryKey())).toObject(dataClass).equals(data);
  }

  // DB 에 정보가 존재함을 확인하는 함수.
  public Completable checkDataExisted(T data) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (cltRef == null) {
      return Completable.error(new Throwable(TAG + FUNC_TAG + "cltRef null"));
    }
    return Completable.create(
        emitter -> cltRef.document(data.getPrimaryKey()).get().addOnCompleteListener(task -> {
          DocumentSnapshot docSnap = task.getResult();
          if (docSnap == null) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "transmission error."));
            return;
          } else if (!docSnap.exists()) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "check the existence of data. [FAIL]"));
            return;
          }
          Log.d(TAG, FUNC_TAG + "check the existence of data. [SUCCESS]" +
              "\nprimary key of data : " + data.getPrimaryKey());
          emitter.onComplete();
          return;
        }));
  }

  // DB 에 정보가 존재하지 않음을 확인하는 함수.
  public Completable checkDataNotExisted(T data) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (cltRef == null) {
      return Completable.error(new Throwable(TAG + FUNC_TAG + "cltRef null"));
    }
    return Completable.create(emitter -> {
      cltRef.document(data.getPrimaryKey()).get().addOnCompleteListener(task -> {
        DocumentSnapshot docSnap = task.getResult();
        if (docSnap == null) {
          emitter.onError(new Throwable(TAG + FUNC_TAG + "transmission error."));
          return;
        } else if (docSnap.exists()) {
          emitter.onError(
              new Throwable(TAG + FUNC_TAG + "check that the data does not exist. [FAIL]"));
          return;
        }
        Log.d(TAG, FUNC_TAG + "check that the data does not exist. [SUCCESS]" +
            "\nprimary key of data : " + data.getPrimaryKey());
        emitter.onComplete();
        return;
      });
    });
  }

  public Completable checkDataEqual(T data) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (cltRef == null) {
      return Completable.error(new Throwable(TAG + FUNC_TAG + "cltRef null"));
    }
    return Completable.create(
        emitter -> cltRef.document(data.getPrimaryKey()).get().addOnCompleteListener(task -> {
          DocumentSnapshot docSnap = task.getResult();
          if (docSnap == null) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "transmission error."));
            return;
          } else if (!docSnap.exists()) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "data not existed."));
            return;
          }
          T dataInDb = docSnap.toObject(dataClass);
          // data 동일 여부 체크.
          if (dataInDb.equals(data)) {
            Log.d(TAG, FUNC_TAG + "data verification [SUCCESS]" +
                "\nprimary key of data : " + data.getPrimaryKey());
            emitter.onComplete();
          } else {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "data verification [FAIL]" +
                "\nprimary key of data : " + data.getPrimaryKey()));
          }
        }));
  }


  public Completable checkDataNotEqual(T data) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (cltRef == null) {
      return Completable.error(new Throwable(TAG + FUNC_TAG + "cltRef null"));
    }
    return Completable.create(
        emitter -> cltRef.document(data.getPrimaryKey()).get().addOnCompleteListener(task -> {
          DocumentSnapshot docSnap = task.getResult();
          if (docSnap == null) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "transmission error."));
            return;
          } else if (!docSnap.exists()) {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "data not existed."));
            return;
          }
          T dataInDb = docSnap.toObject(dataClass);
          // data 동일 여부 체크.
          if (!dataInDb.equals(data)) {
            Log.d(TAG, FUNC_TAG + "data verification [SUCCESS]" +
                "\nprimary key of data : " + data.getPrimaryKey());
            emitter.onComplete();
          } else {
            emitter.onError(new Throwable(TAG + FUNC_TAG + "data verification [FAIL]" +
                "\nprimary key of data : " + data.getPrimaryKey()));
          }
        }));
  }


  // DB 에 데이터 추가/수정하는 함수.
  public void setData(T data, Transaction transaction) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (data.getPrimaryKey().compareTo(Data.AUTO_GENERATED_KEY) == 0) {
      Log.d(TAG, FUNC_TAG + "add new document with auto generated id.");
      DocumentReference docRefToAdd = cltRef.document(); // 새 문서 생성. (id 자동 생성.)
      data.setPrimaryKey(docRefToAdd.getId());  // 자동 생성 id 값 가져와 저장.
    }
    transaction.set(cltRef.document(data.getPrimaryKey()), data);
  }

  // DB 에서 데이터 삭제하는 함수.
  public void deleteData(T data, Transaction transaction) {
    transaction.delete(cltRef.document(data.getPrimaryKey()));
  }

  public void setServerTimestamp(T data, String fieldName, Transaction transaction)
      throws FirebaseFirestoreException {
    transaction
        .update(cltRef.document(data.getPrimaryKey()), fieldName, FieldValue.serverTimestamp());
  }

  public Task<Void> runTransaction(Function<Void> function) {
    return db.runTransaction(function);
  }

  public void clearLisReg() {
    if (this.lisReg != null) {
      this.lisReg.remove();
    }
  }
}
