package com.example.chatting.chat.viewholder.ellipsed;

import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.example.chatting.R;
import com.example.chatting.chat.data.ChatData;
import com.example.chatting.chat.viewholder.ChatViewHolder;

public abstract class EllipsedChatViewHolder extends ChatViewHolder {

  private Button btnFullText = null;
  private String msgFullText = null;
  private String msgEllipsedText = null;

  public EllipsedChatViewHolder(View v) {
    super(v);
    btnFullText = v.findViewById(R.id.button_fullTextButton);
  }

  // ellipsed text 구하는 함수.
  static String getEllipsedText(@NonNull final String msgFullText) {
    return msgFullText.substring(0, 29) + "...생략.";
  }

  public String getMsgfullText() {
    return msgFullText;
  }

  public void setBtnFullTextClickListener(View.OnClickListener clickListener) {
    btnFullText.setOnClickListener(clickListener);
  }

  // ellipsed chat view holder 데이터 설정하는 함수.
  @Override
  public void bind(@NonNull ChatData chatData) {
    this.textView_nickname.setText(chatData.getNickname());
    msgFullText = chatData.getMsg();
    msgEllipsedText = getEllipsedText(msgFullText);
    this.textView_msg.setText(msgEllipsedText);
//    Log.d("ellipsed_bind",
//        "\nmsgFullText :" + msgFullText + "\nmsgEllipsedText " + msgEllipsedText);
  }
}
