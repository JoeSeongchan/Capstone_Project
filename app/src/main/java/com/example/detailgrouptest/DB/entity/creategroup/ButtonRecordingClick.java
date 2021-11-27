package com.example.detailgrouptest.DB.entity.creategroup;

import android.widget.Button;

public class ButtonRecordingClick {

  public final Button button;
  private boolean isClicked = false;

  public ButtonRecordingClick(Button button) {
    this.button = button;
  }

  public void setClicked() {
    this.isClicked = true;
  }

  public void setUnClicked() {
    this.isClicked = false;
  }

  public boolean isClicked() {
    return this.isClicked;
  }
}
