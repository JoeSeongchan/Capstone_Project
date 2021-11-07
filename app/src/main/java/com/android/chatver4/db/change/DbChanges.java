package com.android.chatver4.db.change;

public class DbChanges<T> {

  private int oldIdx;
  private int newIdx;
  private T data;
  private DbChangesType type;

  public DbChanges(int oldIdx, int newIdx, T data, DbChangesType type) {
    this.oldIdx = oldIdx;
    this.newIdx = newIdx;
    this.data = data;
    this.type = type;
  }

  public int getOldIdx() {
    return oldIdx;
  }

  public int getNewIdx() {
    return newIdx;
  }

  public T getData() {
    return data;
  }

  public DbChangesType getType() {
    return type;
  }

  public enum DbChangesType {ADD, MODIFY, DELETE}
}
