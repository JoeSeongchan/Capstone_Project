package com.android.chatver5.db.data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Entity(tableName = "brief_chat_group_table")
public class BriefChatGroup implements Serializable, Data {

  // default constants that fields have.
  private static final String DEF_ID = "def_id";
  private static final String DEF_TITLE = "def_title";
  private static final String DEF_PIC = "def_pic";
  private static final int DEF_CUR_NUM = 0;
  private static final Timestamp DEF_TIME;
  private static final String DEF_LAST_MSG = "def_last_msg";

  static {
    Date dumDate = new Date();
    new GregorianCalendar(1900, 0, 1).setTime(dumDate);
    DEF_TIME = new Timestamp(dumDate);
  }

  // columns (=fields)
  @PrimaryKey
  @NonNull
  private String id;
  @NonNull
  private String title;
  @NonNull
  private String picUri;
  private int curUserNum = DEF_CUR_NUM;
  @ServerTimestamp
  @NonNull
  private Timestamp createdAt = DEF_TIME;
  @Exclude
  private boolean isUpdated = false;
  @Exclude
  private String lastMsg = DEF_LAST_MSG;

  @Ignore
  public BriefChatGroup(@NonNull String id, @NonNull String title, @NonNull String picUri) {
    this.id = id;
    this.title = title;
    this.picUri = picUri;
  }

  @Ignore
  public BriefChatGroup(@NonNull ChatGroup detail) {
    this.id = detail.getId();
    this.title = detail.getTitle();
    this.picUri = detail.getPicUri();
    this.createdAt = detail.getStartDateTime();
  }

  public BriefChatGroup() {
    id = DEF_ID;
    title = DEF_TITLE;
    picUri = DEF_PIC;
  }

  public String getLastMsg() {
    return lastMsg;
  }

  public void setLastMsg(String lastMsg) {
    this.lastMsg = lastMsg;
  }

  public boolean isUpdated() {
    return isUpdated;
  }

  public void setUpdated(boolean updated) {
    isUpdated = updated;
  }

  @NonNull
  public String getPicUri() {
    return picUri;
  }

  public void setPicUri(@NonNull String picUri) {
    this.picUri = picUri;
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public int getCurUserNum() {
    return curUserNum;
  }

  public void setCurUserNum(int curUserNum) {
    this.curUserNum = curUserNum;
  }

  @NonNull
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(@NonNull Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @NonNull
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public void setPrimaryKey(String key) {
    this.id = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BriefChatGroup)) {
      return false;
    }
    BriefChatGroup that = (BriefChatGroup) o;
    return getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
