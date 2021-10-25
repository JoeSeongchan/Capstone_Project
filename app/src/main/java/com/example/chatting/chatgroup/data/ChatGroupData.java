package com.example.chatting.chatgroup.data;

import com.example.chatting.R;
import com.example.chatting.chat.data.ChatData;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ChatGroupData implements Serializable {

  private String chatGroupId;
  private String groupTitle;
  private int repPicSourceUri;
  private List<ChatData> chatDataList;
  private String hostUserId;
  private int maxUserNum;
  private int curUserNum;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private String karaokeId;

  private ChatData recentChatData;
  private LocalDateTime recentChatDateTime;
  private int unReadMsgCount;


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

  public List<ChatData> getChatDataList() {
    return chatDataList;
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

  public LocalDateTime getStartDateTime() {
    return startDateTime;
  }

  public LocalDateTime getEndDateTime() {
    return endDateTime;
  }

  public String getKaraokeId() {
    return karaokeId;
  }

  public ChatData getRecentChatData() {
    return recentChatData;
  }

  public LocalDateTime getRecentChatDateTime() {
    return recentChatDateTime;
  }

  public int getUnReadMsgCount() {
    return unReadMsgCount;
  }

  // update 함수.
  // chat data list 내용물 변경 시 호출할 함수.
  public void update() {
    recentChatData = chatDataList.get(chatDataList.size() - 1);
    recentChatDateTime = recentChatData.getDateTime();
    unReadMsgCount++;
  }

  // Builder 패턴 사용.
  public static final class ChatRoomDataBuilder {

    // final 필드 : 필수 필드.
    // final 아닌 필드 : 필수 아닌 필드.
    private final String chatGroupId;
    private final String groupTitle;
    private final List<ChatData> chatDataList;
    private final String hostUserId;
    private final int maxUserNum;
    private int repPicSourceUri = R.drawable.rep_picture;
    private int curUserNum = 0;
    private LocalDateTime startDateTime = null;
    private LocalDateTime endDateTime = null;
    private String karaokeId = null;

    private ChatRoomDataBuilder(
        final String chatGroupId,
        final String groupTitle,
        final List<ChatData> chatDataList,
        final String hostUserId,
        final int maxUserNum) {
      this.chatGroupId = chatGroupId;
      this.groupTitle = groupTitle;
      this.chatDataList = chatDataList;
      this.hostUserId = hostUserId;
      this.maxUserNum = maxUserNum;
    }

    public static ChatRoomDataBuilder aChatRoomData(
        final String chatGroupId,
        final String groupTitle,
        final List<ChatData> chatDataList,
        final String hostUserId,
        final int maxUserNum) {
      return new ChatRoomDataBuilder(chatGroupId,
          groupTitle,
          chatDataList,
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

    public ChatRoomDataBuilder withStartDateTime(LocalDateTime startDateTime) {
      this.startDateTime = startDateTime;
      return this;
    }

    public ChatRoomDataBuilder withEndDateTime(LocalDateTime endDateTime) {
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
      chatGroupData.chatDataList = this.chatDataList;
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
