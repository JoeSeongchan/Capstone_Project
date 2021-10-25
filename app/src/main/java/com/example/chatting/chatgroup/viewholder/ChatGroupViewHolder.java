package com.example.chatting.chatgroup.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.R;
import com.example.chatting.chatgroup.data.ChatGroupData;

public class ChatGroupViewHolder extends RecyclerView.ViewHolder {

  private View rootView;
  private ImageView ivRepPic;
  private TextView tvGroupTitle;
  private TextView tvRecentMsg;
  private TextView tvDate;
  private TextView tvUnReadMsgCount;

  // find two views inside of root view
  public ChatGroupViewHolder(View v) {
    super(v);
    rootView = v;
    ivRepPic = v.findViewById(R.id.iv_repPic);
    tvGroupTitle = v.findViewById(R.id.tv_groupTitle);
    tvRecentMsg = v.findViewById(R.id.tv_recentMsg);
    tvDate = v.findViewById(R.id.tv_date);
    tvUnReadMsgCount = v.findViewById(R.id.tv_unReadMsgCount);
    v.setClickable(true);
    v.setEnabled(true);
  }

  public void bind(@NonNull ChatGroupData data) {
//    this.ivRepPic;
    this.tvGroupTitle.setText(data.getChatGroupId());
//    this.tvRecentMsg.setText(data.);
//    this.tvDate.setText
//    this.tvUnReadMsgCount
    Log.d("group__bind_vh", "." +
        "\nType of holder : " + this.getClass().getSimpleName() +
        "\nID of chat group : " + data.getChatGroupId() +
        "\n.");
  }

  // 이 view holder 의 정보를 리턴하는 함수.
  public ItemDetails<Long> getItemDetails() {
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
