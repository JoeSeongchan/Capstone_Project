package com.android.chatver4.user;

import com.android.chatver4.db.dbdata.Data;
import java.util.Objects;

public class User implements Data {

  private String id;
  private String pwd;
  private String tel;
  private String pid; // 주민번호.(암호화 예정.)

  public User(String id, String pwd, String tel, String pid) {
    this.id = id;
    this.pwd = pwd;
    this.tel = tel;
    this.pid = pid;
  }

  public User() {
  }

  public User(String id, String pwd) {
    this.id = id;
    this.pwd = pwd;
    this.tel = "";
    this.pid = "";
  }

  public String getId() {
    return id;
  }

  public String getPwd() {
    return pwd;
  }

  public String getTel() {
    return tel;
  }

  public String getPid() {
    return pid;
  }

  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public void setPrimaryKey(String primaryKey) {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return getId().equals(user.getId()) && getPwd().equals(user.getPwd());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPwd());
  }
}
