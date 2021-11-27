package com.example.detailgrouptest.DB.entity;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Party {

  public String pathId;
  public String hostID;
  public String groupName;
  public List<Genre> genreList;
  public Gender gender;
  public int age;
  // 초반 중반 후반
  public List<AgeDetail> ageDetailList;
  public String karaokeId;
  public String karaokeName;
  public Date meetingDate;
  public int curMemberNum;
  public int maxMemberNum;
  public LocalTime meetingStartTime;
  public LocalTime meetingEndTime;

  public Party(String hostID, String groupName,
      List<Genre> genreList, Gender gender, int age,
      List<AgeDetail> ageDetailList, String karaokeId, String karaokeName, Date meetingDate,
      int curMemberNum, int maxMemberNum, LocalTime meetingStartTime,
      LocalTime meetingEndTime) {
    this.hostID = hostID;
    this.groupName = groupName;
    this.genreList = genreList;
    this.gender = gender;
    this.age = age;
    this.ageDetailList = ageDetailList;
    this.karaokeId = karaokeId;
    this.karaokeName = karaokeName;
    this.meetingDate = meetingDate;
    this.curMemberNum = curMemberNum;
    this.maxMemberNum = maxMemberNum;
    this.meetingStartTime = meetingStartTime;
    this.meetingEndTime = meetingEndTime;
  }

  public String getPathId() {
    return pathId;
  }

  public void setPathId(String pathId) {
    this.pathId = pathId;
  }

  public int getCurMemberNum() {
    return curMemberNum;
  }

  public void setCurMemberNum(int curMemberNum) {
    this.curMemberNum = curMemberNum;
  }

  public int getMaxMemberNum() {
    return maxMemberNum;
  }

  public void setMaxMemberNum(int maxMemberNum) {
    this.maxMemberNum = maxMemberNum;
  }

  public String getHostID() {
    return hostID;
  }

  public void setHostID(String hostID) {
    this.hostID = hostID;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public List<Genre> getGenreList() {
    return genreList;
  }

  public void setGenreList(List<Genre> genreList) {
    this.genreList = genreList;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<AgeDetail> getAgeDetailList() {
    return ageDetailList;
  }

  public void setAgeDetailList(
      List<AgeDetail> ageDetailList) {
    this.ageDetailList = ageDetailList;
  }

  public String getKaraokeId() {
    return karaokeId;
  }

  public void setKaraokeId(String karaokeId) {
    this.karaokeId = karaokeId;
  }

  public String getKaraokeName() {
    return karaokeName;
  }

  public void setKaraokeName(String karaokeName) {
    this.karaokeName = karaokeName;
  }

  public Date getMeetingDate() {
    return meetingDate;
  }

  public void setMeetingDate(Date meetingDate) {
    this.meetingDate = meetingDate;
  }

  public LocalTime getMeetingStartTime() {
    return meetingStartTime;
  }

  public void setMeetingStartTime(LocalTime meetingStartTime) {
    this.meetingStartTime = meetingStartTime;
  }

  public LocalTime getMeetingEndTime() {
    return meetingEndTime;
  }

  public void setMeetingEndTime(LocalTime meetingEndTime) {
    this.meetingEndTime = meetingEndTime;
  }

  public enum AgeDetail {
    EARLY, MID, LATE
  }

  public enum Genre {
    FREE, TOP, BALAD, NORMAL_SONG, POP, TROT, HIPHOP, JPOP, OTHER, OLD_SONG
  }

  public enum Gender {
    FEMALE, MALE, FREE
  }
}
