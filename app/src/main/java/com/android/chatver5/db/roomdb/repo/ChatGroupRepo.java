package com.android.chatver5.db.roomdb.repo;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.ChatGroup;
import com.android.chatver5.db.roomdb.dao.ChatGroupDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import java.util.List;

public class ChatGroupRepo {

  private ChatGroupDao dao;
  private LiveData<List<ChatGroup>> allData;

  public ChatGroupRepo(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    dao = db.chatGroupDao();
    allData = dao.getAll();
  }

  public LiveData<List<ChatGroup>> getAll() {
    return allData;
  }

  public void insert(ChatGroup data) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(data));
  }

  public void delete(ChatGroup item) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(item));
  }

  public void deleteAll() {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteAll());
  }
}
