package com.example.chatting.chatgroup.viewholder;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
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

  // Representative Picture Source 설정하는 함수.
  private void setRepPicSrc(@DrawableRes int id) {
    // drawable 폴더에서 representative picture 의 bitmap 을 가져온다.
    BitmapDrawable img = (BitmapDrawable) rootView.getResources()
        .getDrawable(R.drawable.rep_picture);

    // ImageView 에 이미지 설정.
    ivRepPic.setImageDrawable(img);
  }

  // View - Data binding 작업 수행하는 함수.
  public void bind(@NonNull ChatGroupData data) {
    setRepPicSrc(R.drawable.rep_picture);
    this.tvGroupTitle.setText(data.getGroupTitle());
    this.tvRecentMsg.setText(data.getRecentChatData().getMsg());
    this.tvDate.setText(data.getRecentChatDateTime().toString());
    this.tvUnReadMsgCount.setText(data.getUnReadMsgCount());
    Log.d("vh_group", " - bind view holder in view holder class." +
        "\nType of holder : " + this.getClass().getSimpleName() +
        "\nID of chat group : " + data.getChatGroupId() +
        "\n.");
  }

  // Selection 구현에 필요.
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

  // Selection 구현에 필요.
  // 이 view holder 가 선택된 경우, 할 행동 지정하는 함수.
  public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
    if (selectionTracker != null && selectionTracker
        .isSelected((long) getBindingAdapterPosition())) {
    }
  }
}
