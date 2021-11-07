package com.android.chatver4.chat.viewholder;


import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.android.chatver4.R;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.parentholder.ParentViewHolder;

public abstract class ChatViewHolder extends ParentViewHolder<Chat> {

  protected TextView tvMsg;
  protected View rootView;
  protected TextView tvNickName;


  // find two views inside of root view
  public ChatViewHolder(@NonNull View itemView) {
    super(itemView);
    tvNickName = itemView.findViewById(R.id.tv_nickname);
    tvMsg = itemView.findViewById(R.id.tv_msg);
    rootView = itemView;
    itemView.setClickable(true);
    itemView.setEnabled(true);
  }
}
