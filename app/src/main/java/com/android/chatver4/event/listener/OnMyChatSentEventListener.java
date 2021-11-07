package com.android.chatver4.event.listener;

import com.android.chatver4.event.MyChatSentEvent;
import java.util.EventListener;

public interface OnMyChatSentEventListener extends EventListener {

  void onChatSent(MyChatSentEvent event);
}
