package com.android.chatver5.db.dbtracker;

import com.android.chatver5.db.data.Data;

public interface DbTracker<T extends Data> {

  void getUpdateInRealTime();

  void dispose();
}
