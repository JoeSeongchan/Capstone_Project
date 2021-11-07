package com.android.chatver4.chat.data;


import com.android.chatver4.db.dbdata.Data;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import java.io.Serializable;

public class Chat implements Serializable, Data {

  public static final String DEFAULT_MSG = "default_msg";
  public static final String DEFAULT_NICK = "default_nick";
  private String id;
  private String msg;
  private String nickname;
  @ServerTimestamp
  private Timestamp timestamp;

  public Chat(String msg, String nickname) {
    this.id = AUTO_GENERATED_KEY;
    this.msg = msg;
    this.nickname = nickname;
    this.timestamp = null;
  }

  public Chat() {
    this.id = AUTO_GENERATED_KEY;
    msg = DEFAULT_MSG;
    nickname = DEFAULT_NICK;
    timestamp = null;
  }

  public String getId() {
    return this.id;
  }

  public String getMsg() {
    return msg;
  }

  public String getNickname() {
    return nickname;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  @Override
  public String getPrimaryKey() {
    return this.id;
  }

  @Override
  public void setPrimaryKey(String primaryKey) {
    this.id = primaryKey;
  }
}
