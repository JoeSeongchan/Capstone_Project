package com.android.chatver5.db.roomdb.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.ChatGroup;
import com.android.chatver5.db.roomdb.repo.ChatGroupRepo;
import java.util.List;

public class ChatGroupViewModel extends AndroidViewModel {

  private final LiveData<List<ChatGroup>> allData;
  private ChatGroupRepo repo;

  public ChatGroupViewModel(@NonNull Application application) {
    super(application);
    repo = new ChatGroupRepo(application);
    allData = repo.getAll();
  }

  // method to return a cached list of words.
  public LiveData<List<ChatGroup>> getAll() {
    return allData;
  }

  public void insert(ChatGroup chat) {
    repo.insert(chat);
  }

  public void delete(ChatGroup item) {
    repo.delete(item);
  }

  public void deleteAll() {
    repo.deleteAll();
  }
}
