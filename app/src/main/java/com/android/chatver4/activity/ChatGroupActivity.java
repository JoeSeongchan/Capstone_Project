package com.android.chatver4.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver4.R;
import com.android.chatver4.chatgroup.adapter.ChatGroupAdapter;
import com.android.chatver4.chatgroup.data.ChatGroup;
import com.android.chatver4.chatgroup.data.ChatGroupForView;
import com.android.chatver4.db.Db;
import com.android.chatver4.utilities.Utilities;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ChatGroupActivity extends AppCompatActivity {

  private static final String TAG = "CG_Activity_R";

  // recycler view 세팅.
  private RecyclerView recyclerView;
  private ChatGroupAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;

  // firestore 데이터베이스
  private Db<ChatGroupForView> groupForViewDb;
  private Db<ChatGroup> groupDb;

  private String userLoginId;   // LoginActivity 에서 전달 받은 유저 아이디.

  private CompositeDisposable compositeDisposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUi();  // ui 설정.
    setupDb();  // db 설정.
    notifyChangeToAdapter();
  }

  // ui 설정하는 함수.
  private void setupUi() {
    // inflate.
    setContentView(R.layout.activity_chat_group);

    // recycler view 세팅.
    recyclerView = findViewById(R.id.recyclerView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    adapter = new ChatGroupAdapter(this);
    recyclerView.setAdapter(adapter);

//    // LoginActivity 로부터 전달 받은 유저 아이디 저장.
//    Intent inputIntent = getIntent();
//    userLoginId = inputIntent.getStringExtra("userLoginId");
    // test
    userLoginId = "CHOSC";

    // 유저 아이디 화면에 표시.
    TextView tvLoginId = findViewById(R.id.tvLoginId);
    String msg = tvLoginId.getText().toString().replace("login_id", userLoginId);
    tvLoginId.setText(msg);
  }

  // db 설정하는 함수.
  public void setupDb() {
    groupForViewDb = new Db<>("user_collection/" +
        userLoginId +
        "/chat_group_collection",
        ChatGroupForView.class);
    groupDb = new Db<>("chat_group_collection", ChatGroup.class);
    compositeDisposable = new CompositeDisposable();
  }

  // add group 버튼 클릭시 할 행동.
  public void btnAddGroupLis(View v) {
    final String FUNC_TAG = Utilities.makeFuncTag();

    // 새로운 그룹 생성.
    groupDb.runTransaction(transaction -> {
      ChatGroup newGroup = new ChatGroup("dummy01", userLoginId, 10);
      groupDb.setData(newGroup, transaction);
      ChatGroupForView newGroupForView = new ChatGroupForView(
          newGroup.getId(),
          newGroup.getTitle(),
          newGroup.getPicUri());
      groupForViewDb.setData(newGroupForView, transaction);
      return null;
    }).addOnCompleteListener(
        task -> Log.d(TAG, FUNC_TAG + "add new chat group & chat group for view [SUCCESS]"))
        .addOnFailureListener(
            err -> Log.w(TAG, FUNC_TAG + "add new chat group & chat group for view [FAIL]\n" +
                err.getMessage()));
  }

  private void notifyChangeToAdapter() {
    final String FUNC_TAG = Utilities.makeFuncTag();
    Disposable disp = groupForViewDb.getChangesInRealTime().subscribe(changes -> {
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

  @Override
  protected void onPause() {
    super.onPause();
//    final String FUNC_TAG = " - onPause\n";
//    super.onPause();
//    Log.d(LOG_TAG, FUNC_TAG);
//    this.userDb.clearLisReg();
//    this.compositeDisposable.clear();
  }
}