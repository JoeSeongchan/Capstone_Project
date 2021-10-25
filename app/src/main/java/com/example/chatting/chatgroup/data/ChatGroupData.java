package com.example.chatting.chatgroup.data;

import com.example.chatting.R;
import java.io.Serializable;

public class ChatGroupData implements Serializable {

  private String chatGroupId;
  private String groupTitle;
  private int repPicSourceUri;
  private String chatDataListId;
  private String hostUserId;
  private int maxUserNum;
  private int curUserNum;
  private String startDateTime;
  private String endDateTime;
  private String karaokeId;

  public ChatGroupData() {
  }

  // Getter
  public String getChatGroupId() {
    return chatGroupId;
  }

  public String getGroupTitle() {
    return groupTitle;
  }

  public int getRepPicSourceUri() {
    return repPicSourceUri;
  }

  public String getChatDataListId() {
    return chatDataListId;
  }

  public String getHostUserId() {
    return hostUserId;
  }

  public int getMaxUserNum() {
    return maxUserNum;
  }

  public int getCurUserNum() {
    return curUserNum;
  }

  public String getStartDateTime() {
    return startDateTime;
  }

  public String getEndDateTime() {
    return endDateTime;
  }

  public String getKaraokeId() {
    return karaokeId;
  }

  // Builder 패턴 사용.
  public static final class ChatRoomDataBuilder {

    // final 필드 : 필수 필드.
    // final 아닌 필드 : 필수 아닌 필드.
    private final String chatGroupId;
    private final String groupTitle;
    private final String chatDataListId;
    private final String hostUserId;
    private final int maxUserNum;
    private int repPicSourceUri = R.drawable.rep_picture;
    private int curUserNum = 0;
    private String startDateTime = "LocalDateTime.now().toString()";
    private String endDateTime = "LocalDateTime.now().toString()";
    private String karaokeId = "karaoke";

    private ChatRoomDataBuilder(
        final String chatGroupId,
        final String groupTitle,
        final String chatDataListId,
        final String hostUserId,
        final int maxUserNum) {
      this.chatGroupId = chatGroupId;
      this.groupTitle = groupTitle;
      this.chatDataListId = chatDataListId;
      this.hostUserId = hostUserId;
      this.maxUserNum = maxUserNum;
    }

    public static ChatRoomDataBuilder aChatRoomData(
        final String chatGroupId,
        final String groupTitle,
        final String chatDataListId,
        final String hostUserId,
        final int maxUserNum) {
      return new ChatRoomDataBuilder(chatGroupId,
          groupTitle,
          chatDataListId,
          hostUserId,
          maxUserNum);
    }

    public ChatRoomDataBuilder withRepPicSourceUri(int repPicSourceUri) {
      this.repPicSourceUri = repPicSourceUri;
      return this;
    }

    public ChatRoomDataBuilder withCurUserNum(int curUserNum) {
      this.curUserNum = curUserNum;
      return this;
    }

    public ChatRoomDataBuilder withStartDateTime(String startDateTime) {
      this.startDateTime = startDateTime;
      return this;
    }

    public ChatRoomDataBuilder withEndDateTime(String endDateTime) {
      this.endDateTime = endDateTime;
      return this;
    }

    public ChatRoomDataBuilder withKaraokeId(String karaokeId) {
      this.karaokeId = karaokeId;
      return this;
    }


    public ChatGroupData build() {
      ChatGroupData chatGroupData = new ChatGroupData();
      chatGroupData.chatGroupId = this.chatGroupId;
      chatGroupData.groupTitle = this.groupTitle;
      chatGroupData.repPicSourceUri = this.repPicSourceUri;
      chatGroupData.chatDataListId = this.chatDataListId;
      chatGroupData.hostUserId = this.hostUserId;
      chatGroupData.maxUserNum = this.maxUserNum;
      chatGroupData.curUserNum = this.curUserNum;
      chatGroupData.startDateTime = this.startDateTime;
      chatGroupData.endDateTime = this.endDateTime;
      chatGroupData.karaokeId = this.karaokeId;
      return chatGroupData;
    }
  }
}
