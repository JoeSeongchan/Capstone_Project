package com.android.chatver4.parentholder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ParentViewHolder<T> extends RecyclerView.ViewHolder {

  protected T data;

  public ParentViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  public T getData() {
    return data;
  }

  public abstract void bind(@NonNull T data);
}
