package com.android.chatver4.event;

import com.android.chatver4.chat.adapter.ChatAdapter;
import java.util.EventObject;

public class MyChatSentEvent extends EventObject {

  public MyChatSentEvent(Object source) {
    super(source);
  }

  @Override
  public ChatAdapter getSource() {
    ChatAdapter chatAdapterSrc = (ChatAdapter) source;
    return chatAdapterSrc;
  }
}
