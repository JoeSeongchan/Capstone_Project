package com.example.chatting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.chat.selection.ChatDetailsLookUp;
import com.example.chatting.chatgroup.adapter.ChatGroupAdapter;
import com.example.chatting.chatgroup.data.ChatGroupData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupActivity extends AppCompatActivity {

  // 접근 권한 코드.
  private static final int REQ_CODE_PERMISSION = 0;
  // recycler view 세팅.
  private RecyclerView recyclerView;
  private ChatGroupAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private List<ChatGroupData> dataList = new ArrayList<>();
  private String myNick = "chosc"; // 첫번째 단말기의 닉네임.
  private SelectionTracker<Long> selectionTracker;
  // xml 컴포넌트 매칭되는 객체.
  //
  // firebase 데이터베이스
  private FirebaseDatabase chatGroupDb;
  private DatabaseReference chatGroupDbRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();  // ui 설정.
    setupDb();  // db 설정.
  }

  // ui 설정하는 함수.
  private void setupUI() {
    // inflate.
    setContentView(R.layout.activity_chat_group);

    // recycler view 세팅.
    recyclerView = findViewById(R.id.recyclerView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new ChatGroupAdapter(dataList, this);
    recyclerView.setAdapter(adapter);
    setupSelectionTracker();
    adapter.setSelectionTracker(selectionTracker);
  }

  // db 설정하는 함수.
  public void setupDb() {
    // 데이터베이스 초기화.
    chatGroupDb = FirebaseDatabase.getInstance();
    chatGroupDbRef = chatGroupDb.getReference();

    // 새로운 데이터가 데이터베이스에 저장되었을 때 수행할 동작 지정.
    chatGroupDbRef.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Log.d("db_group", " - get new group data from DB" +
            "\nType of Data" + ChatGroupData.class.getSimpleName() +
            "\nContents of chat : " + snapshot.getValue().toString() +
            "\n.");
        ChatGroupData chatGroup = snapshot.getValue(ChatGroupData.class);
        adapter.addChatGroup(chatGroup);
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

  // add group 버튼 클릭시 할 행동.
  public void btnAddGroupClickListener(View v) {
    Toast toast = Toast.makeText(this, "btn Add is clicked.", Toast.LENGTH_SHORT);
    toast.show();
//    String msg = editText_chat.getText().toString();
//    if (msg != null) {
//      ChatData chat = new ChatData();
//      chat.setNickname(myNick);
//      chat.setMsg(msg);
//      chatDbRef.push().setValue(chat);
//    }
  }

  // selection 설정하는 함수.
  private void setupSelectionTracker() {
    this.selectionTracker = new SelectionTracker.Builder<>("selection_id", recyclerView,
        new StableIdKeyProvider(recyclerView), new ChatDetailsLookUp(recyclerView),
        StorageStrategy.createLongStorage())
        .withSelectionPredicate(SelectionPredicates.createSelectAnything()).build();
  }
}