package com.android.chatver5.activity.lifecyclerobserver;

import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.chatver5.db.dbtracker.DbTracker;
import java.util.ArrayList;
import java.util.List;

public class DbTrackerLifeCycleManager implements LifecycleObserver {

  List<DbTracker<?>> dbTrackerList;

  public DbTrackerLifeCycleManager() {
    dbTrackerList = new ArrayList<>();
  }

  public void add(DbTracker<?> dbTracker) {
    dbTrackerList.add(dbTracker);
  }

  @OnLifecycleEvent(Event.ON_DESTROY)
  void dispose() {
    dbTrackerList.forEach(DbTracker::dispose);
  }
}
