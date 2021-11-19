package com.android.chatver5.db.roomdb.db;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.android.chatver5.db.data.BriefChatGroup;
import com.android.chatver5.db.data.Chat;
import com.android.chatver5.db.data.ChatGroup;
import com.android.chatver5.db.data.TypeConverter;
import com.android.chatver5.db.roomdb.dao.BriefChatGroupDao;
import com.android.chatver5.db.roomdb.dao.ChatDao;
import com.android.chatver5.db.roomdb.dao.ChatGroupDao;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// exportSchema = setting for data migration.
@Database(entities = {ChatGroup.class, BriefChatGroup.class,
    Chat.class}, version = 1, exportSchema = false)
@TypeConverters({TypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

  private static final int NUMBER_OF_THREADS = 4;
  public static final ExecutorService databaseWriteExecutor =
      Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  private static final RoomDatabase.Callback dbClb;

  // singleton pattern,
  private static volatile AppDatabase INSTANCE;

  static {
    dbClb = new Callback() {
      @Override
      public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);

        databaseWriteExecutor.execute(() -> {
          // Populate the database in the background.
          // If you want to start with more words, just add them.
          BriefChatGroupDao briefChatGroupDao = INSTANCE.briefChatGroupDao();
          // briefChatGroupDao.insert(new BriefChatGroup());
          briefChatGroupDao.insert(new BriefChatGroup("dum01", "dum01", "dum01"));
          // we can add several chat group data.
          // ex) chatGroupDao.insert(...)
        });
      }
    };
  }

  public static AppDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AppDatabase.class, "app_database")
              .addCallback(dbClb)
              .build();
        }
      }
    }
    return INSTANCE;
  }

  public abstract ChatGroupDao chatGroupDao();

  public abstract BriefChatGroupDao briefChatGroupDao();

  public abstract ChatDao chatDao();
}
