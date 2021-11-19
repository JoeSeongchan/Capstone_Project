package com.android.chatver5.db.roomdb.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.android.chatver5.db.data.BriefChatGroup;
import com.android.chatver5.db.roomdb.repo.BriefChatGroupRepo;
import java.util.List;

public class BriefChatGroupViewModel extends AndroidViewModel {

  private final LiveData<List<BriefChatGroup>> allData;
  private BriefChatGroupRepo repo;

  public BriefChatGroupViewModel(@NonNull Application application) {
    super(application);
    repo = new BriefChatGroupRepo(application);
    allData = repo.getAll();
  }

  // method to return a cached list of words.
  public LiveData<List<BriefChatGroup>> getAll() {
    return allData;
  }

  public void insert(BriefChatGroup item) {
    repo.insert(item);
  }

  public void delete(BriefChatGroup item) {
    repo.delete(item);
  }

  public void deleteAll() {
    repo.deleteAll();
  }
}
