package com.android.chatver5.db.roomdb.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.Party;
import com.android.chatver5.db.roomdb.repo.ChatGroupRepo;
import java.util.List;

public class ChatGroupViewModel extends AndroidViewModel {

  private final LiveData<List<Party>> allData;
  private ChatGroupRepo repo;

  public ChatGroupViewModel(@NonNull Application application) {
    super(application);
    repo = new ChatGroupRepo(application);
    allData = repo.getAll();
  }

  // method to return a cached list of words.
  public LiveData<List<Party>> getAll() {
    return allData;
  }

  public void insert(Party chat) {
    repo.insert(chat);
  }

  public void delete(Party item) {
    repo.delete(item);
  }

  public void deleteAll() {
    repo.deleteAll();
  }
}
