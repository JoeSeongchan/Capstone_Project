package com.android.chatver4.utilities;

public final class Utilities {

  public static String makeFuncTag() {
    String curFuncName = Thread.currentThread().getStackTrace()[3].getMethodName();
    return " - " + curFuncName + "\n";
  }

  public static String extractUpper(String full) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < full.length(); i++) {
      char c = full.charAt(i);
      if ((int) c >= 65 && (int) c <= 90) {
        builder.append(c);
      }
    }

    return builder.toString();
  }
}
