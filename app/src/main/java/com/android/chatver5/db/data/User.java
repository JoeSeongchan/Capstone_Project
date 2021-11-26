package com.android.chatver5.db.data;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class User implements Data, Serializable {

  private static final String DEF_NAME = "def_name";
  private static final String DEF_ID = "def_id";
  private static final String DEF_NICK = "def_nick";
  private static final String DEF_EMAIL = "def_email";
  private static final String DEF_TEL = "def_tel";
  private static final Date DEF_DATE = new GregorianCalendar(1991, 0, 1)
      .getTime();

  @NonNull
  private String name = DEF_NAME;
  @NonNull
  private String id = DEF_ID;
  @NonNull
  private String nick = DEF_NICK;
  @NonNull
  private String email = DEF_EMAIL;
  @NonNull
  private String tel = DEF_TEL;
  @NonNull
  private Date dateOfBirth = DEF_DATE;

  public User() {
  }

  public User(@NonNull String name, @NonNull String id, @NonNull String nick,
      @NonNull String email, @NonNull String tel, @NonNull Date dateOfBirth) {
    this.name = name;
    this.id = id;
    this.nick = nick;
    this.email = email;
    this.tel = tel;
    this.dateOfBirth = dateOfBirth;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  @NonNull
  public String getNick() {
    return nick;
  }

  public void setNick(@NonNull String nick) {
    this.nick = nick;
  }

  @NonNull
  public String getEmail() {
    return email;
  }

  public void setEmail(@NonNull String email) {
    this.email = email;
  }

  @NonNull
  public String getTel() {
    return tel;
  }

  public void setTel(@NonNull String tel) {
    this.tel = tel;
  }

  @NonNull
  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(@NonNull Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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
    return getId().equals(user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @NonNull
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  @Override
  public void setPrimaryKey(@NonNull String key) {
    // do nothing.
    // not working.
    // we can't change user's primary key (email) by setter.
  }
}
