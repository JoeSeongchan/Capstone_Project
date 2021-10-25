package com.example.chatting.chat.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatData implements Serializable {

  private String msg;
  private String nickname;
  private LocalDateTime dateTime;

  public String getMsg() {
    return msg;
  }

  public String getNickname() {
    return nickname;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }
}
