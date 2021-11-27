package com.example.detailgrouptest.DB;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class GroupDb {
    public String hostID;
    public String groupName;
    public int[] gener;
    public String gender;
    public int age;
    public int[] ageSub;
    public int groupP;
    public String singroomId;
    public String singroomName;
    public String groupDate;

    public int startTime_hour;  //모임시작시간
    public int startTime_min;
    public int endTime_hour;    //모임종료시간
    public int endTime_min;

    public GroupDb() {
        // Default constructor required for calls to DataSnapshot.getValue(GroupDetail.class)
    }

    public GroupDb(String hostID, String groupName, int[] gener, String gender, int age, int[] ageSub,
                   int groupP, String singroomId, String singroomName, String groupDate, int startTime_hour, int startTime_min,
                   int endTime_hour, int endTime_min) {
        this.hostID = hostID;
        this.groupName = groupName;
        this.gener = gener;
        this.gender = gender;
        this.age = age;
        this.ageSub = ageSub;
        this.groupP = groupP;
        this.singroomId = singroomId;
        this.singroomName = singroomName;
        this.groupDate = groupDate;
        this.startTime_hour = startTime_hour;
        this.startTime_min = startTime_min;
        this.endTime_hour = endTime_hour;
        this.endTime_min = endTime_min;
    }

    public String getHostID() { return hostID; }
    public void setHostID(String hostID) { this.hostID = hostID; }

    public String getGroupName() {  return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int[] getAgeSub() { return ageSub; }
    public void setAgeSub(int[] ageSub) { this.ageSub = ageSub; }

    public int getGroupP() { return groupP; }
    public void setGroupP(int groupP) { this.groupP = groupP; }

    public String getSingroomId() { return singroomId; }
    public void setSingroomId(String singroomId) { this.singroomId = singroomId; }

    public String getSingroomName() { return singroomName; }
    public void setSingroomName(String singroomName) { this.singroomName = singroomName; }

    public int getStartTime_hour() { return startTime_hour; }
    public void setStartTime_hour(int startTime_hour) { this.startTime_hour = startTime_hour; }

    public int getStartTime_min() { return startTime_min; }
    public void setStartTime_min(int startTime_min) { this.startTime_min = startTime_min; }

    public int getEndTime_hour() { return endTime_hour; }
    public void setEndTime_hour(int endTime_hour) { this.endTime_hour = endTime_hour; }

    public int getEndTime_min() { return endTime_min; }
    public void setEndTime_min(int endTime_min) { this.endTime_min = endTime_min; }
}
