package com.android.chatver5.db.roomdb.repo;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.Party;
import com.android.chatver5.db.roomdb.dao.PartyDao;
import com.android.chatver5.db.roomdb.db.AppDatabase;
import java.util.List;

public class ChatGroupRepo {

  private PartyDao dao;
  private LiveData<List<Party>> allData;

  public ChatGroupRepo(Application application) {
    AppDatabase db = AppDatabase.getDatabase(application);
    dao = db.chatGroupDao();
    allData = dao.getAll();
  }

  public LiveData<List<Party>> getAll() {
    return allData;
  }

  public void insert(Party data) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(data));
  }

  public void delete(Party item) {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(item));
  }

  public void deleteAll() {
    AppDatabase.databaseWriteExecutor.execute(() -> dao.deleteAll());
  }
}
