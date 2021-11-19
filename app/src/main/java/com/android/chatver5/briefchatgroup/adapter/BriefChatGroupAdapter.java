package com.android.chatver5.briefchatgroup.adapter;


import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import com.android.chatver5.briefchatgroup.viewholder.BriefChatGroupViewHolder;
import com.android.chatver5.briefchatgroup.viewholder.BriefChatGroupViewHolder.onItemClickListener;
import com.android.chatver5.db.data.BriefChatGroup;

public class BriefChatGroupAdapter extends ListAdapter<BriefChatGroup, BriefChatGroupViewHolder> {

  private onItemClickListener onItemClickListener;

  public BriefChatGroupAdapter(
      @NonNull ItemCallback<BriefChatGroup> diffCallback,
      onItemClickListener onItemClickListener) {
    super(diffCallback);
    this.onItemClickListener = onItemClickListener;
    this.setHasStableIds(true);
  }

  @NonNull
  @Override
  public BriefChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    BriefChatGroupViewHolder viewHolder = BriefChatGroupViewHolder.create(parent);
    viewHolder.setOnItemClickListener(onItemClickListener);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull BriefChatGroupViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  public BriefChatGroup getItem(int pos) {
    return super.getItem(pos);
  }

  @Override
  public long getItemId(int position) {
    return getCurrentList().get(position).hashCode();
  }

  public static class BriefChatGroupDiff extends DiffUtil.ItemCallback<BriefChatGroup> {

    @Override
    public boolean areItemsTheSame(@NonNull BriefChatGroup oldItem,
        @NonNull BriefChatGroup newItem) {
      return oldItem.getPrimaryKey().compareTo(newItem.getPrimaryKey()) == 0 &&
          oldItem.getLastMsg().compareTo(newItem.getLastMsg()) == 0;
    }

    @Override
    public boolean areContentsTheSame(@NonNull BriefChatGroup oldItem,
        @NonNull BriefChatGroup newItem) {
      return oldItem.getPrimaryKey().compareTo(newItem.getPrimaryKey()) == 0 &&
          oldItem.getLastMsg().compareTo(newItem.getLastMsg()) == 0;
    }
  }
}
