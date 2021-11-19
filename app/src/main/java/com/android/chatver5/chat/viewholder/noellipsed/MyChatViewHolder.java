package com.android.chatver5.chat.viewholder.noellipsed;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.android.chatver5.R;
import com.android.chatver5.chat.viewholder.ChatBindable;
import com.android.chatver5.chat.viewholder.ChatUtilities;
import com.android.chatver5.db.data.Chat;

public class MyChatViewHolder extends ViewHolder implements ChatBindable {

  private final View root;
  private final TextView tvNick, tvMsg, tvDate;

  public MyChatViewHolder(@NonNull View itemView) {
    super(itemView);
    root = itemView;
    tvNick = root.findViewById(R.id.tv_nickName_myChat);
    tvMsg = root.findViewById(R.id.tv_msg_myChat);
    tvDate = root.findViewById(R.id.tv_date_myChat);
  }

  public static MyChatViewHolder create(ViewGroup parent) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat, parent, false);
    return new MyChatViewHolder(view);
  }

  public void bind(@NonNull Chat chat) {
    this.tvNick.setText(chat.getNickname());
    this.tvMsg.setText(chat.getMsg());
    this.tvDate.setText(ChatUtilities.makeDateStr(chat));
  }
}
