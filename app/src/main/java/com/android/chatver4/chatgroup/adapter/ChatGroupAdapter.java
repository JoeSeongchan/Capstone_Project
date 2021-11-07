package com.android.chatver4.chatgroup.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver4.R;
import com.android.chatver4.chatgroup.data.ChatGroupForView;
import com.android.chatver4.chatgroup.viewholder.ChatGroupViewHolder;
import com.android.chatver4.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupViewHolder> {

  public static final String TAG = "ChatGroupAdapter_R";
  private final Context context;
  private final List<ChatGroupForView> dataList;

  public ChatGroupAdapter(Context context) {
    this.context = context;
    this.dataList = new ArrayList<>();
  }

  // 새로운 view holder 만드는 함수.
  @NonNull
  @Override
  public ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.chat_group, parent, false);
    return new ChatGroupViewHolder(view);
  }

  // holder - data 매칭해주는 함수. (기존 vh 재활용.)
  @Override
  public void onBindViewHolder(@NonNull ChatGroupViewHolder holder, int position) {
    holder.bind(dataList.get(position));
  }

  // 데이터 목록의 크기 구하는 함수.
  @Override
  public int getItemCount() {
    return dataList.size();
  }

  // 채팅 그룹 추가하는 함수.
  public void addData(ChatGroupForView data) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Log.d(TAG, FUNC_TAG +
        "id : " + data.getId() +
        "\ntitle : " + data.getTitle());
    dataList.add(data);
    notifyItemInserted(dataList.size() - 1);
  }

  // 채팅 그룹의 정보 수정하는 함수.
  public void modifyData(ChatGroupForView data, int pos) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Log.d(TAG, FUNC_TAG +
        "id : " + data.getId() +
        "\ntitle : " + data.getTitle());
    dataList.set(pos, data);
    notifyItemChanged(pos);
  }

  // 채팅 그룹 삭제하는 함수.
  public void deleteData(int pos) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Log.d(TAG, FUNC_TAG +
        "id : " + dataList.get(pos).getId() +
        "\ntitle : " + dataList.get(pos).getTitle());
    dataList.remove(pos);
    notifyItemRemoved(pos);
    notifyItemRangeChanged(pos, getItemCount());
  }
}
