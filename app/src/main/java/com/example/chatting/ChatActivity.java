package com.example.chatting;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.chat.adapter.ChatAdapter;
import com.example.chatting.chat.data.ChatData;
import com.example.chatting.chat.selection.ChatDetailsLookUp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

  // 접근 권한 코드.
  private static final int REQ_CODE_PERMISSION = 0;
  // recycler view 세팅.
  private RecyclerView recyclerView;
  private ChatAdapter chatAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private List<ChatData> chatList = new ArrayList<>();
  private String myNick = "chosc"; // 첫번째 단말기의 닉네임.
  private SelectionTracker<Long> selectionTracker;
  // xml 컴포넌트 매칭되는 객체.
  private Button button_send;
  private EditText editText_chat;
  // firebase 데이터베이스
  private FirebaseDatabase chatDatabase;
  private DatabaseReference chatDbRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityCompat.requestPermissions(this, new String[]{permission.READ_EXTERNAL_STORAGE},
        REQ_CODE_PERMISSION);
  }

  // 권한 요청할 때 할 행동.
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQ_CODE_PERMISSION) {
      for (int grantResult : grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
          finish();
          Log.d("permission", "permission is denied. finish.");
          return;
        }
      }
      setupUI();  // ui 설정.
      setupDb();  // db 설정.
    }
  }

  // ui 설정하는 함수.
  private void setupUI() {
    // inflate.
    setContentView(R.layout.activity_chat);

    button_send = findViewById(R.id.button_send);
    editText_chat = findViewById(R.id.editText_chat);

    // recycler view 세팅.
    recyclerView = findViewById(R.id.recyclerView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    chatAdapter = new ChatAdapter(chatList, myNick, this);
    recyclerView.setAdapter(chatAdapter);
    setupSelectionTracker();
    chatAdapter.setSelectionTracker(selectionTracker);
  }

  // db 설정하는 함수.
  public void setupDb() {
    // 데이터베이스 초기화.
    chatDatabase = FirebaseDatabase.getInstance();
    chatDbRef = chatDatabase.getReference();

    // 데이터베이스 데이터 입력 시 동작 지정.
    chatDbRef.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Log.d("ChatActivity - setupDb", "Add new chat" +
            "\nType of Data" + ChatData.class.getSimpleName() +
            "\nContents of chat : " + snapshot.getValue().toString() +
            "\n.");
        ChatData chat = snapshot.getValue(ChatData.class);
        chatAdapter.addChat(chat);
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
  }

  // send 버튼 클릭시 할 행동.
  public void button_send_clickListener(View v) {
    String msg = editText_chat.getText().toString();
    if (msg != null) {
      ChatData chat = new ChatData();
      chat.setNickname(myNick);
      chat.setMsg(msg);
      chatDbRef.push().setValue(chat);
    }
  }

  // selection 설정하는 함수.
  private void setupSelectionTracker() {
    this.selectionTracker = new SelectionTracker.Builder<>("selection_id", recyclerView,
        new StableIdKeyProvider(recyclerView), new ChatDetailsLookUp(recyclerView),
        StorageStrategy.createLongStorage())
        .withSelectionPredicate(SelectionPredicates.createSelectAnything()).build();
  }
}