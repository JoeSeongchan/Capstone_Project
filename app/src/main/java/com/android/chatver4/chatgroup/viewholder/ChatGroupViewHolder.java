package com.android.chatver4.chatgroup.viewholder;


import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver4.R;
import com.android.chatver4.chatgroup.data.ChatGroupForView;

public class ChatGroupViewHolder extends RecyclerView.ViewHolder {

  final private View rootView;
  final private ImageView ivPic;
  final private TextView tvTitle;
  final private TextView tvRecentMsg;
  final private TextView tvDateTime;
  final private TextView tvUnReadMsgCount;


  public ChatGroupViewHolder(View v) {
    super(v);
    rootView = v;
    ivPic = v.findViewById(R.id.iv_repPic);
    tvTitle = v.findViewById(R.id.tv_groupTitle);
    tvRecentMsg = v.findViewById(R.id.tv_recentMsg);
    tvDateTime = v.findViewById(R.id.tv_date);
    tvUnReadMsgCount = v.findViewById(R.id.tv_unReadMsgCount);
    v.setClickable(true);
    v.setEnabled(true);
  }

  // Representative Picture Source 설정하는 함수.
  private void setRepPicSrc(@DrawableRes int id) {
    // drawable 폴더에서 representative picture 의 bitmap 을 가져온다.
    BitmapDrawable img =
        (BitmapDrawable) ResourcesCompat
            .getDrawable(rootView.getResources(), R.drawable.rep_picture, null);
    // ImageView 에 이미지 설정.
    ivPic.setImageDrawable(img);
  }

  // View - Data binding 작업 수행하는 함수.
  public void bind(@NonNull ChatGroupForView data) {
    setRepPicSrc(R.drawable.rep_picture);
    this.tvTitle.setText(data.getTitle());
    Log.d("ChatGroupViewHolder", " - bind" +
        "\nGroup title : " + data.getId());
    this.tvRecentMsg.setText("0"); //data.getRecentChatData().getMsg()
    this.tvDateTime.setText("0"); //data.getRecentChatDateTime().toString()
    this.tvUnReadMsgCount.setText("0"); //data.getUnReadMsgCount()
    Log.d("vh_group", " - bind view holder in view holder class." +
        "\nType of holder : " + this.getClass().getSimpleName() +
        "\nID of chat group : " + data.getId() +
        "\n.");
  }
}
