package com.example.capstone.database;

public class groupdisplay {
    int distance;
    String local;
    int max;
    int member;
    long starttime;
    String title;

    public groupdisplay(){}

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String Local) {
        this.local = local;
    }
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getMember() {
        return member;
    }
    public void setMember(int member) {
        this.member = member;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getStarttime() {
        return starttime;
    }
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }



    //이거는 그룹을 생성할때 사용하는 부분
    public groupdisplay(String title, String local,  int distance , int max, int member, long starttime) {
        this.distance = distance;
        this.title = title;
        this.max = max;
        this.local = local;
        this.starttime = starttime;
        this.member = member;
    }
}
