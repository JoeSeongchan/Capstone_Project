package com.android.chatver5.db.roomdb.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.android.chatver5.db.data.BriefChatGroup;
import io.reactivex.rxjava3.core.Completable;
import java.util.List;

@Dao
public interface BriefChatGroupDao {

  @Query("SELECT * FROM brief_chat_group_table")
  LiveData<List<BriefChatGroup>> getAll();

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Completable insert(BriefChatGroup item);

  @Query("DELETE FROM brief_chat_group_table")
  Completable deleteAll();

  @Delete
  Completable delete(BriefChatGroup item);

  @Update
  Completable update(BriefChatGroup item);

  @Query("UPDATE brief_chat_group_table SET isUpdated = 'true' WHERE id IN (:ids)")
  Completable markUpdatedItems(List<String> ids);

  @Query("DELETE FROM brief_chat_group_table WHERE isUpdated = 'false'")
  Completable deleteNotUpdated();

  @Query("UPDATE brief_chat_group_table SET isUpdated = 'false'")
  Completable resetUpdateState();

  @Query("UPDATE brief_chat_group_table SET lastMsg = :lastMsg WHERE id = :groupId")
  Completable updateLastMsg(String groupId, String lastMsg);
}
