package com.android.chatver4.db.dbdata;

public class SampleData implements Data {

  String id;
  String data;

  public SampleData() {
  }

  public SampleData(String data) {
    this.id = AUTO_GENERATED_KEY;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public String getData() {
    return data;
  }

  @Override
  public String getPrimaryKey() {
    return this.getId();
  }

  @Override
  public void setPrimaryKey(String primaryKey) {
    this.id = primaryKey;
  }
}
