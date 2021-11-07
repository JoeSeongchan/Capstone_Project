package com.android.chatver4.chat.viewholder.ellipsed;


import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.android.chatver4.R;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.chat.viewholder.ChatViewHolder;

public abstract class EllipsedChatViewHolder extends ChatViewHolder {

  private final Button btnFullText;

  public EllipsedChatViewHolder(@NonNull View itemView) {
    super(itemView);
    btnFullText = itemView.findViewById(R.id.btn_fullTextButton);
  }

  // full msg text 구하는 함수.
  public String getMsgFullText() {
    return data.getMsg();
  }

  // ellipsed msg text 구하는 함수.
  public String getMsgEllipsedText() {
    return getMsgFullText().substring(0, 29) + "...생략.";
  }

  public void setBtnFullTextClickListener(View.OnClickListener clickListener) {
    btnFullText.setOnClickListener(clickListener);
  }

  @Override
  public void bind(@NonNull Chat chat) {
    data = chat;
    this.tvNickName.setText(chat.getNickname());
    this.tvMsg.setText(getMsgEllipsedText());
  }

  public void setTextViewMsgWidth(final int width) {
    this.tvMsg.setWidth(width);
  }
}
