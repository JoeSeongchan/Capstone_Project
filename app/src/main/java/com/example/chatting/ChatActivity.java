package com.example.chatting;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.chat.adapter.ChatAdapter;
import com.example.chatting.chat.data.ChatData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

  // recycler view 세팅.
  private RecyclerView recyclerView;
  private ChatAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private List<ChatData> chatList;
  private String myNick = "chosc"; // 첫번째 단말기의 닉네임.

  // xml 컴포넌트 매칭되는 객체.
  private Button button_send;
  private EditText editText_chat;

  // firebase 데이터베이스
  private FirebaseDatabase chatDatabase;
  private DatabaseReference chatDbRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // inflate (xml->memory) 수행.
    setContentView(R.layout.activity_chat);

    button_send = findViewById(R.id.button_send);
    editText_chat = findViewById(R.id.editText_chat);

    button_send.setOnClickListener(view -> {
      String msg = editText_chat.getText().toString();
      if (msg != null) {
        ChatData chat = new ChatData();
        chat.setNickname(myNick);
        chat.setMsg(msg);
        chatDbRef.push().setValue(chat);
      }
    });

    // recycler view 세팅.
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(false);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    chatList = new ArrayList<>();
    adapter = new ChatAdapter(chatList, myNick, this);
    recyclerView.setAdapter(adapter);

    // 데이터베이스 선언 & 할당.
    chatDatabase = FirebaseDatabase.getInstance();
    chatDbRef = chatDatabase.getReference();

    // 데이터베이스 데이터 입력 시 동작 지정.
    chatDbRef.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Log.d("FIREBASE", snapshot.getValue().toString());
        Log.d("CHAT", snapshot.getValue().toString());
        ChatData chat = snapshot.getValue(ChatData.class);
        adapter.addChat(chat);
      }

      @Override
      public void onChildChanged(@NonNull DataSnapshot snapshot,
          @Nullable String previousChildName) {

      }

      @Override
      public void onChildRemoved(@NonNull DataSnapshot snapshot) {

      }

      @Override
      public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    // 테스트 삼아 firebase에 데이터 삽입.
    ChatData chat = new ChatData();
    chat.setNickname("test data");
    chat.setMsg("test data msg");
    chatDbRef.push().setValue(chat);
  }
}