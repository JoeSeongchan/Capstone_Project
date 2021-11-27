package com.example.detailgrouptest.DB.entity;

import java.util.List;

public class Party {

  public String hostID;
  public String groupName;

  public String gender;

  // 나이
  public int age;
  public List<AgeDetail> ageDetailList;
  public List<Genre> genreList;
  public String singroomId;
  public String singroomName;
  public String groupDate;

  public int startTime_hour;  //모임시작시간
  public int startTime_min;
  public int endTime_hour;    //모임종료시간
  public int endTime_min;

  public enum AgeDetail {
    EARLY, MID, LATE
  }

  public enum Genre {
    FREE, TOP, BALAD, NORMAL_SONG, POP, TROT, HIPHOP, JPOP
  }


}
