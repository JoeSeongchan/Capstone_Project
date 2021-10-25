package com.example.chatting.chatgroup.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.R;
import com.example.chatting.chatgroup.data.ChatGroupData;
import com.example.chatting.chatgroup.viewholder.ChatGroupViewHolder;
import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupViewHolder> {


  private final List<ChatGroupData> dataList;   // 데이터.

  private Context context;

  @Nullable
  private SelectionTracker<Long> selectionTracker;

  // 데이터 설정.
  public ChatGroupAdapter(List<ChatGroupData> dataList, Context context) {
    this.dataList = dataList;
    this.context = context;
    // id 를 사용해 item 식별.
    setHasStableIds(true);
  }

  // 새로운 view holder 만드는 함수.
  @NonNull
  @Override
  public ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.chat_group, parent, false);
    Log.d("ChatGroupAdapter", " - onCreateViewHolder" +
        "\nType of holder : " + ChatGroupViewHolder.class.getSimpleName());
    return new ChatGroupViewHolder(view);
  }

  // holder - data 매칭해주는 함수. (기존 vh 재활용.)
  @Override
  public void onBindViewHolder(@NonNull ChatGroupViewHolder holder, int position) {
    holder.bind(getData(position));
    Log.d("ChatGroupAdapter", " - onBindViewHolder" +
        "\nType of holder : " + holder.getClass().getSimpleName() +
        "\nID of chat group located in #" + position + " : " + getData(position).getChatGroupId() +
        "\n.");
    holder.setSelectionTracker(selectionTracker);
  }

  // Selection 구현에 필요.
  @Override
  public long getItemId(int position) {
    return position;
  }

  // 데이터 목록의 크기 구하는 함수.
  @Override
  public int getItemCount() {
    return dataList.size();
  }

  // 특정 위치의 chat group data 구하는 함수.
  private ChatGroupData getData(final int position) {
    return this.dataList.get(position);
  }

  // 채팅 그룹 추가하는 함수.
  public void addChatGroup(ChatGroupData data) {
    dataList.add(data);
    // 새로 추가한 데이터의 위치를 알려준다.
    notifyItemInserted(dataList.size() - 1);
  }

  // chat group data 변경 시 업데이트하는 함수.
  // 데이터 목록 & view holder 업데이트.
  public void updateChatGroup(ChatGroupData inputData) {
    for (int i = 0; i < dataList.size(); i++) {
      ChatGroupData data = dataList.get(i);
      if (data.getChatGroupId() == inputData.getChatGroupId()) {
        dataList.set(i, inputData);   // 데이터 목록 업데이트.
        notifyItemChanged(i);         // view holder 업데이트.
        return;
      }
    }
  }

  // Selection 구현에 사용.
  // Selection 설정하는 함수.
  public void setSelectionTracker(final SelectionTracker<Long> selectionTracker) {
    this.selectionTracker = selectionTracker;
  }
}
