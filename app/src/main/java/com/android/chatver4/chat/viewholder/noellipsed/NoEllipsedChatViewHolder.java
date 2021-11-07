package com.android.chatver4.chat.viewholder.noellipsed;


import android.view.View;
import androidx.annotation.NonNull;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.chat.viewholder.ChatViewHolder;

public abstract class NoEllipsedChatViewHolder extends ChatViewHolder {

  public NoEllipsedChatViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override
  public void bind(@NonNull Chat chat) {
    data = chat;
    this.tvNickName.setText(chat.getNickname());
    this.tvMsg.setText(chat.getMsg());
  }
}
