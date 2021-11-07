package com.android.chatver4.chatgroup.data;


import com.android.chatver4.db.dbdata.Data;
import java.io.Serializable;

public class ChatGroupForView implements Serializable, Data {

  String id;
  String title;
  String picUri;

  public ChatGroupForView(String id, String title, String picUri) {
    this.id = id;
    this.title = title;
    this.picUri = picUri;
  }

  public ChatGroupForView() {
  }

  public String getPicUri() {
    return picUri;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public void setPrimaryKey(String primaryKey) {
    // do nothing.
  }
}
