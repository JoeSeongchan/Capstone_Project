package com.example.chatting.chat.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.R;
import com.example.chatting.chat.data.ChatData;

public abstract class ChatViewHolder extends RecyclerView.ViewHolder {

  protected TextView textView_msg;
  protected View rootView;
  // data of view holder
  protected TextView textView_nickname;

  // find two views inside of root view
  public ChatViewHolder(View v) {
    super(v);
    textView_nickname = v.findViewById(R.id.textView_nickname);
    textView_msg = v.findViewById(R.id.textView_msg);
    rootView = v;
    v.setClickable(true);
    v.setEnabled(true);
  }

  public void bind(@NonNull ChatData chatData) {
    textView_nickname.setText(chatData.getNickname());
    textView_msg.setText(chatData.getMsg());
  }
}
