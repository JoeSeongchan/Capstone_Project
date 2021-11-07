package com.android.chatver4.chatgroup.data;


import com.android.chatver4.db.dbdata.Data;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class ChatGroup implements Serializable, Data {

  private String id;
  private String title;
  private String hostId;
  private int maxUserNum;
  private String picUri;
  private int curUserNum;
  private Timestamp startDateTime;
  private Timestamp endDateTime;
  private String karaokeId;
  @ServerTimestamp
  private Timestamp createdAt;
  @Exclude
  private List<String> chatList;

  public ChatGroup() {
    this.chatList = new ArrayList<>();
  }

  public ChatGroup(
      String title,
      String hostId,
      int maxUserNum
  ) {
    this.id = AUTO_GENERATED_KEY;
    this.title = title;
    this.hostId = hostId;
    this.maxUserNum = maxUserNum;
    this.chatList = new ArrayList<>();
  }

  public void addChat(String data) {
    chatList.add(data);
  }

  public void modifyChat(String data, int pos) {
    chatList.set(pos, data);
  }

  public void deleteChat(int pos) {
    chatList.remove(pos);
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getPicUri() {
    return picUri;
  }

  public void setPicUri(String picUri) {
    this.picUri = picUri;
  }

  public String getHostId() {
    return hostId;
  }

  public int getMaxUserNum() {
    return maxUserNum;
  }

  public int getCurUserNum() {
    return curUserNum;
  }

  public void setCurUserNum(int curUserNum) {
    this.curUserNum = curUserNum;
  }

  public Timestamp getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(Timestamp startDateTime) {
    this.startDateTime = startDateTime;
  }

  public Timestamp getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(Timestamp endDateTime) {
    this.endDateTime = endDateTime;
  }

  public String getKaraokeId() {
    return karaokeId;
  }

  public void setKaraokeId(String karaokeId) {
    this.karaokeId = karaokeId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String getPrimaryKey() {
    // firestore 서버에 add 할 때 서버가 자동으로 path 값을 생성한다.
    // path 값을 primary key 값으로 설정한다.
    return getId();
  }

  @Override
  public void setPrimaryKey(String primaryKey) {
    this.id = primaryKey;
  }
}
