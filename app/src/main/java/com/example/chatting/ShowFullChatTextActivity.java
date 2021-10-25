package com.example.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowFullChatTextActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_full_chat_text);
    Intent inputIntent = getIntent();
    String inputMsgFullText = inputIntent.getStringExtra("msgFullText");
    TextView textView_msgFullText = findViewById(R.id.textView_msgFullText);
    textView_msgFullText.setText(inputMsgFullText);
  }
}