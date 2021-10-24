package com.example.chatting.chat.viewholder.noellipsed;

import android.view.View;
import androidx.annotation.NonNull;
import com.example.chatting.chat.data.ChatData;
import com.example.chatting.chat.viewholder.ChatViewHolder;

public abstract class NoEllipsedChatViewHolder extends ChatViewHolder {

  public NoEllipsedChatViewHolder(View v) {
    super(v);
  }

  @Override
  public void bind(@NonNull ChatData chatData) {
    this.textView_nickname.setText(chatData.getNickname());
    this.textView_msg.setText(chatData.getMsg());
  }
}
