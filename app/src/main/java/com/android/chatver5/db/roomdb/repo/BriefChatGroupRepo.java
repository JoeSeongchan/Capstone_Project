package com.android.chatver5.db.roomdb.repo;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.BriefChatGroup;
import com.android.chatver5.db.roomdb.dao.BriefChatGroupDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import java.util.List;

public class BriefChatGroupRepo {

  private BriefChatGroupDao dao;
  private LiveData<List<BriefChatGroup>> allData;

  public BriefChatGroupRepo(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    dao = db.briefChatGroupDao();
    allData = dao.getAll();
  }

  public LiveData<List<BriefChatGroup>> getAll() {
    return allData;
  }

  public void insert(BriefChatGroup data) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(data));
  }

  public void delete(BriefChatGroup item) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(item));
  }

  public void deleteAll() {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteAll());
  }

  public void updateLastMsg(String groupId, String lastMsg) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.updateLastMsg(groupId, lastMsg));
  }
}
