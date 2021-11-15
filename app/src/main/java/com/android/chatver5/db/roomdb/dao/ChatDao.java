package com.android.chatver5.db.roomdb.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.android.chatver5.db.data.Chat;
import io.reactivex.rxjava3.core.Completable;
import java.util.List;

@Dao
public interface ChatDao {

  @Query("SELECT * FROM chat_table ORDER BY timestamp ASC")
  LiveData<List<Chat>> getAll();

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Completable insert(Chat data);

  @Query("DELETE FROM chat_table")
  Completable deleteAll();

  @Delete
  Completable delete(Chat item);

  @Update
  Completable update(Chat item);

  @Query("UPDATE chat_table SET isUpdated = 'true' WHERE chatId IN (:ids)")
  Completable markUpdatedItems(List<String> ids);

  @Query("DELETE FROM chat_table WHERE isUpdated = 'false' ")
  Completable deleteNotUpdated();

  @Query("UPDATE chat_table SET isUpdated = 'false'")
  Completable resetUpdateState();
}
