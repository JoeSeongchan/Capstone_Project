package com.example.chatting.chat.viewholder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.R;
import com.example.chatting.chat.data.ChatData;

public abstract class ChatViewHolder extends RecyclerView.ViewHolder {

  private static final int MSG_MAX_LENGTH = 30;
  protected TextView textView_msg;
  protected View rootView;
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

  public void setTextViewMsgWidth(final int width) {
    this.textView_msg.setWidth(width);
  }

  public abstract void bind(@NonNull ChatData chatData);

  // 이 view holder 의 정보를 리턴하는 함수.
  public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
    return new ItemDetails<Long>() {
      @Override
      public int getPosition() {
        return getBindingAdapterPosition();
      }

      @Nullable
      @Override
      public Long getSelectionKey() {
        return getItemId();
      }
    };
  }

  // 이 view holder 가 선택된 경우, 할 행동 지정하는 함수.
  public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
    if (selectionTracker != null && selectionTracker
        .isSelected((long) getBindingAdapterPosition())) {

    }
  }
}
