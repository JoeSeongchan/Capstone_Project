package com.android.chatver5.db.firedbmanager;

import androidx.annotation.NonNull;
import com.android.chatver5.db.data.Data;

public interface DbManager<T extends Data> {

  void getUpdateInRealTime();

  void addItem(@NonNull T item);

  void deleteItem(@NonNull T item);

  void modifyItem(@NonNull T item);

  void dispose();
}
