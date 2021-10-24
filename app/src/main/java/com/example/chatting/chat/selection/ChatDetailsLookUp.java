package com.example.chatting.chat.selection;

import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.chat.viewholder.ChatViewHolder;

public class ChatDetailsLookUp extends ItemDetailsLookup<Long> {

  private RecyclerView recyclerView;

  public ChatDetailsLookUp(final RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
  }

  @Nullable
  @Override
  public ItemDetails<Long> getItemDetails(@NonNull MotionEvent motionEvent) {
    View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
    if (view != null) {
      ChatViewHolder chatViewHolder = (ChatViewHolder) recyclerView.getChildViewHolder(view);
      return chatViewHolder.getItemDetails();
    }
    return null;
  }
}
