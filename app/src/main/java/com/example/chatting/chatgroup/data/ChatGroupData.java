package com.example.chatting.chatgroup.data;

import com.example.chatting.chat.data.ChatData;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatGroupData implements Serializable {

  // (ID, 제목, 대표 사진, 가장 최근에 올라온 채팅 내용, recent 한 채팅 올라온 날짜/시간, 아직 읽지 않은 메시지 수 )
  // chat group view 에 표시될 데이터.
  private String chatGroupId;
  private String groupTitle;
  private String repPicSourceUri;
  private ChatData recentChatData;
  private LocalDateTime dateTimeOfRecentChat;
  private int unReadMsgCount;

  // (호스트 유저 ID, 최대 인원 수, 현재 인원 수, 모임 시작 날짜/시간, 모임 종료 날짜/시간, 노래방 ID)
  private String hostUserId;
  private int maxUserNum;
  private int curUserNum;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private String karaokeId;

  // Getter


  public String getChatGroupId() {
    return chatGroupId;
  }

  public String getGroupTitle() {
    return groupTitle;
  }

  public String getRepPicSourceUri() {
    return repPicSourceUri;
  }

  public ChatData getRecentChatData() {
    return recentChatData;
  }

  public LocalDateTime getDateTimeOfRecentChat() {
    return dateTimeOfRecentChat;
  }

  public int getUnReadMsgCount() {
    return unReadMsgCount;
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

  // Builder 패턴 사용.
  public static final class ChatRoomDataBuilder {

    // final 필드 : 필수 필드.
    // final 아닌 필드 : 필수 아닌 필드.
    private String chatGroupId;
    private String groupTitle;
    private String repPicSourceUri;
    private ChatData recentChatData;
    private LocalDateTime dateTimeOfRecentChat;
    private int unReadMsgCount;
    private String hostUserId;
    private int maxUserNum;
    private int curUserNum;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String karaokeId;

    private ChatRoomDataBuilder(final String chatGroupId,
        final String hostUserId,
        final int maxUserNum,
        final int curUserNum,
        final LocalDateTime startDateTime) {
      this.chatGroupId = chatGroupId;
      this.hostUserId = hostUserId;
      this.maxUserNum = maxUserNum;
      this.curUserNum = curUserNum;
      this.startDateTime = startDateTime;
    }

    public static ChatRoomDataBuilder aChatRoomData(
        final String chatGroupId,
        final String hostUserId,
        final int maxUserNum,
        final int curUserNum,
        final LocalDateTime startDateTimes) {
      return new ChatRoomDataBuilder(chatGroupId,
          hostUserId,
          maxUserNum,
          curUserNum,
          startDateTimes);
    }

    public ChatRoomDataBuilder withRepresentativePicture(String representativePicture) {
      this.representativePicture = representativePicture;
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
      ChatGroupData chatRoomData = new ChatGroupData();
      chatRoomData.endDateTime = this.endDateTime;
      chatRoomData.karaokeId = this.karaokeId;
      chatRoomData.curUserNum = this.curUserNum;
      chatRoomData.maxUserNum = this.maxUserNum;
      chatRoomData.chatGroupId = this.chatGroupId;
      chatRoomData.hostUserId = this.hostUserId;
      chatRoomData.startDateTime = this.startDateTime;
      chatRoomData.representativePicture = this.representativePicture;
      return chatRoomData;
    }
  }
}
