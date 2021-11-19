package com.android.chatver5.db.roomdb.repo;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.Chat;
import com.android.chatver5.db.roomdb.dao.ChatDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import java.util.List;

public class ChatRepo {

  private ChatDao dao;
  private LiveData<List<Chat>> allData;

  public ChatRepo(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    dao = db.chatDao();
    allData = dao.getAll();
  }

  public LiveData<List<Chat>> getAll() {
    return allData;
  }

  public void insert(Chat data) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(data));
  }

  public void delete(Chat item) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(item));
  }

  public void deleteAll() {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteAll());
  }
}
