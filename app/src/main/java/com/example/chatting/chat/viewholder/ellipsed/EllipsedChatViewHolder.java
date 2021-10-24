package com.example.chatting.chat.viewholder.ellipsed;

import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.example.chatting.R;
import com.example.chatting.chat.data.ChatData;
import com.example.chatting.chat.viewholder.ChatViewHolder;

public abstract class EllipsedChatViewHolder extends ChatViewHolder {

  private Button fullTextButton = null;
  private String msg_fullText = null;
  private String msg_ellipsedText = null;

  public EllipsedChatViewHolder(View v) {
    super(v);
    fullTextButton = v.findViewById(R.id.button_fullTextButton);
  }

  // ellipsed text 구하는 함수.
  static String getEllipsedText(@NonNull final String msgFullText) {
    return msgFullText.substring(0, 29) + "...생략.";
  }

  public int getMsgLineCount() {

  }

  // ellipsed chat view holder 데이터 설정하는 함수.
  @Override
  public void bind(@NonNull ChatData chatData) {
    this.textView_nickname.setText(chatData.getNickname());
    msg_fullText = chatData.getMsg();
    msg_ellipsedText = getEllipsedText(msg_fullText);
    this.textView_msg.setText(msg_ellipsedText);
  }
}
