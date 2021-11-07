package com.android.chatver4.parentadapter;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.android.chatver4.db.dbdata.Data;
import com.android.chatver4.parentholder.ParentViewHolder;
import java.util.List;

public abstract class ParentAdapter<T extends Data> extends Adapter<ParentViewHolder<T>> {

  protected final String TAG;
  protected final List<T> dataList;  // 데이터 목록
  protected Context context;

  public ParentAdapter(List<T> dataList, Context context) {
    TAG = "Adapter";
    this.dataList = dataList;
    this.context = context;
    // id 를 사용해 item 식별.
    setHasStableIds(true);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }


  // 새로운 chat view holder 만드는 함수.
  @NonNull
  public abstract ParentViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

  // chat view holder 의 내용 수정하는 함수. (layout manager 에 의해 호출.)
  public abstract void onBindViewHolder(@NonNull ParentViewHolder<T> holder, int position);

  // 데이터 목록 안 데이터의 타입 구하는 함수.
  // 내 채팅 or 상대방 채팅.
  public abstract int getItemViewType(int position);

  // 데이터 목록의 크기 구하는 함수.
  @Override
  public int getItemCount() {
    return dataList.size();
  }

  public T getData(int position) {
    return this.dataList.get(position);
  }

  //
  // 데이터 조작 함수.
  //
  public void addData(T data) {
    dataList.add(data);
    // notify the position of newly added item
    notifyItemInserted(dataList.size() - 1);
  }

  public void modifyData(T chat, int pos) {
    dataList.set(pos, chat);
    notifyItemChanged(pos);
  }

  public void deleteData(int pos) {
    dataList.remove(pos);
    notifyItemRemoved(pos);
    notifyItemRangeChanged(pos, getItemCount());
  }
}
