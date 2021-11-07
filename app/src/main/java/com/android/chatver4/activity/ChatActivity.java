package com.android.chatver4.activity;


import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver4.R;
import com.android.chatver4.chat.adapter.ChatAdapter;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.db.variousdb.ChatDb;
import com.android.chatver4.utilities.Utilities;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

  private static final String TAG = "ChatActivity_R";

  // 접근 권한 코드.
  private static final int REQ_CODE_PERMISSION = 0;
  // rxjava 관리.
  private CompositeDisposable compositeDisposable;
  // recycler view 세팅.
  private RecyclerView recyclerView;
  private ChatAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private List<Chat> chatList = new ArrayList<>();
  private String myNick = "CHOSC"; // 첫번째 단말기의 닉네임.
  // xml 컴포넌트 매칭되는 객체.
  private EditText etMsg;
  // firestore 데이터베이스
  private ChatDb chatDb;

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
    final String FUNC_TAG = Utilities.makeFuncTag();
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQ_CODE_PERMISSION) {
      for (int grantResult : grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
          finish();
          Log.d(TAG, FUNC_TAG + "permission is denied. finish.");
          return;
        }
      }
      setupUI();  // ui 설정.
      setupDb();  // db 설정.
      notifyChangeToAdapter();
    }
  }

  // ui 설정하는 함수.
  private void setupUI() {
    // inflate.
    setContentView(R.layout.activity_chat);
    etMsg = findViewById(R.id.editText_chat);

    etMsg.setOnKeyListener((v, keyCode, event) -> {
      if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
        sendMsg();
        etMsg.setText("");
        return true;
      }
      return false;
    });

    // recycler view 세팅.
    recyclerView = findViewById(R.id.recyclerView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new ChatAdapter(chatList, this, myNick);
    recyclerView.setAdapter(adapter);

    adapter.setOnMyChatSentEventListener(
        event -> recyclerView.smoothScrollToPosition(event.getSource().getItemCount() - 1));
  }

  // db 설정하는 함수.
  public void setupDb() {
    compositeDisposable = new CompositeDisposable();
    chatDb = new ChatDb("chat_collection", Chat.class);
  }

  private void notifyChangeToAdapter() {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Disposable disp = chatDb.getChangesInRealTime().subscribe(changes -> {
      switch (changes.getType()) {
        case ADD:
          adapter.addData(changes.getData());
          break;
        case MODIFY:
          adapter.modifyData(changes.getData(), changes.getOldIdx());
          break;
        case DELETE:
          adapter.deleteData(changes.getOldIdx());
          break;
      }
    }, err -> {
      Log.w(TAG, FUNC_TAG + err.getMessage());
    });
    compositeDisposable.add(disp);
  }

  private void sendMsg() {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Log.d(TAG, FUNC_TAG + "send message.");
    String msg = etMsg.getText().toString();
    Chat newChat = new Chat(msg, myNick);
    // 새로운 챗 생성.
    chatDb.runTransaction(transaction -> {
      chatDb.setData(newChat, transaction);
      return null;
    }).addOnCompleteListener(
        task -> Log.d(TAG, FUNC_TAG + "add new chat [SUCCESS]"))
        .addOnFailureListener(
            err -> Log.w(TAG, FUNC_TAG + "add new chat [FAIL]\n" +
                err.getMessage()));

  }

  // send 버튼 클릭시 할 행동.
  public void btnSendClickListener(View v) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Log.d(TAG, FUNC_TAG + "button 'send' is clicked.");
    sendMsg();
    etMsg.setText("");
  }
}