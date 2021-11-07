package com.android.chatver4.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.chatver4.R;
import com.android.chatver4.db.Db;
import com.android.chatver4.user.User;
import com.android.chatver4.utilities.Utilities;
import com.google.firebase.firestore.FirebaseFirestoreException;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class LoginActivity extends AppCompatActivity {

  public static final String TAG = "LoginActivity_R";

  // UI 컴포넌트.
  private Button btnLogin, btnSignUp;
  private EditText etId, etPwd;

  // firestore 데이터베이스.
  private Db<User> userDb;

  private CompositeDisposable compositeDisposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setDb();
    setUi();
  }

  // 로그인 서버 DB 설정하는 함수.
  private void setDb() {
    userDb = new Db<>("user_collection", User.class);
    compositeDisposable = new CompositeDisposable();
  }

  // UI 컴포넌트 접근 가능하게 미리 View 변수 초기화하는 함수.
  private void setUi() {
    this.etId = findViewById(R.id.etId);
    this.etPwd = findViewById(R.id.etPwd);
    this.btnLogin = findViewById(R.id.btnLogin);
    this.btnSignUp = findViewById(R.id.btnSignUp);
  }

  // 채팅 그룹 리스트 화면으로 전환하는 함수.
  private void moveToChatGroupAct(String id) {
    Toast.makeText(this, "user login id : " + id, Toast.LENGTH_SHORT).show();
//    Intent intent = new Intent(this, ChatGroupActivity.class);
//    intent.putExtra("userLoginId", id);
//    startActivity(intent);
  }

  public void login(View v) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    final String id = ((TextView) findViewById(R.id.etId)).getText().toString();
    final String pwd = ((TextView) findViewById(R.id.etPwd)).getText().toString();
    User user = new User(id, pwd);
    compositeDisposable.add(userDb.checkDataEqual(user)
        .subscribe(() -> {
          //로그인 성공.
          Log.d(TAG, FUNC_TAG + "account check [SUCCESS]" +
              "\nid : " + id);
          // 채팅 그룹 리스트 화면으로 전환.
          moveToChatGroupAct(id);
        }, err -> Log.w(TAG, FUNC_TAG + "account check [FAIL]" +
            "\n" + err.getMessage())));
  }

  public void signUp(View v) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    final String id = ((TextView) findViewById(R.id.etId)).getText().toString();
    final String pwd = ((TextView) findViewById(R.id.etPwd)).getText().toString();
    User user = new User(id, pwd);
    // 계정 정보 확인 & 계정 생성.
    userDb.runTransaction(transaction -> {
      if (!userDb.checkDataExisted(user, transaction)) {
        userDb.setData(user, transaction);
        return null;
      } else {
        throw new FirebaseFirestoreException("account is already existed",
            FirebaseFirestoreException.Code.ABORTED);
      }
    }).addOnSuccessListener(task -> {
      Log.d(TAG, FUNC_TAG + "create an account [SUCCESS]" +
          "\nid : " + id);
      moveToChatGroupAct(id);
    }).addOnFailureListener(err ->
        Log.w(TAG, FUNC_TAG + "create an account [FAIL]" +
            "\n" + err.getMessage()));
  }

  @Override
  protected void onPause() {
    final String FUNC_TAG = " - onPause\n";
    super.onPause();
    Log.d(TAG, FUNC_TAG);
    this.userDb.clearLisReg();
    this.compositeDisposable.clear();
  }
}
