package com.android.chatver5.chat.viewholder;

import com.android.chatver5.db.data.Chat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUtilities {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
  private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

  public static String makeDateStr(Chat chat) {
    Date date = chat.getTimestamp().toDate();
    return dateFormat.format(date) + "\n" +
        timeFormat.format(date);
  }
}
