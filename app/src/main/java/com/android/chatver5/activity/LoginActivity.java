package com.android.chatver5.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.chatver5.R;
import com.android.chatver5.db.data.User;
import com.android.chatver5.db.firedb.Db;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity {

  private static final int RC_SIGN_IN = 9001;
  private final ActivityResultContract<Intent, String> contractRegister =
      new ActivityResultContract<Intent, String>() {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Intent input) {
          return input;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent result) {
          if (resultCode != Activity.RESULT_OK || result == null) {
            return null;
          }
          return result.getStringExtra("email");
        }
      };
  private Button btnDefaultLogin;
  private TextView tvRegister;
  private EditText etEmail, etPwd;
  private final ActivityResultLauncher<Intent> launcherRegister = registerForActivityResult(
      contractRegister, email -> {
        if (email == null) {
          Utilities.log(LogType.d, "register fail.");
        } else {
          Utilities.log(LogType.d, "register success.");
          etEmail.setText(email);
        }
      });
  private FirebaseAuth authDb;
  private Db<User> userDb;

  private void initDb() {
    authDb = FirebaseAuth.getInstance();
    userDb = new Db<>("user_list", User.class);
  }

  private void initView() {
    tvRegister = findViewById(R.id.tv_register_login);
    btnDefaultLogin = findViewById(R.id.btn_login_login);
    etEmail = findViewById(R.id.et_email_login);
    etPwd = findViewById(R.id.et_pwd_login);
  }

  private void btnRegisterLis(@NonNull View view) {
    launcherRegister.launch(new Intent(this, RegisterActivity.class));
  }

  private void btnLoginLis(@NonNull View view) {
    String email = etEmail.getText().toString().trim();
    String pwd = etPwd.getText().toString().trim();

    authDb.signInWithEmailAndPassword(email, pwd)
        .addOnSuccessListener(authResult -> {
          Utilities.log(LogType.d, "login success.");
          moveToNextAct();
        })
        .addOnFailureListener(err -> Utilities.log(LogType.d, "login error.\n" + err.getMessage()));
  }

  // 다음 Activity 로 넘어가는 함수. (로그인 확인)
  private void moveToNextAct() {
    FirebaseUser currentUser = authDb.getCurrentUser();
    // send intent
    if (currentUser == null) {
      Toast.makeText(this, "login fail.", Toast.LENGTH_SHORT).show();
      Utilities.log(LogType.w, "login fail.");
    } else {
      Toast.makeText(this, "login success."
          + "\nid : " + currentUser.getUid()
          + "\nemail : " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(this, ChatGroupActivity.class);
      userDb.getData(currentUser.getUid())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(nick -> {
            intent.putExtra("nick", nick.getNick());
            startActivity(intent);
            finish();
          }, err -> Utilities.log(LogType.w, "err : " + err.getMessage()));
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    moveToNextAct();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    initDb();
    initView();
    // 회원 가입 버튼이 눌리면,
    tvRegister.setOnClickListener(this::btnRegisterLis);

    //로그인 버튼이 눌리면,
    btnDefaultLogin.setOnClickListener(this::btnLoginLis);

    // 이미 로그인되어 있으면, 다음 Activity 로 전환.
    moveToNextAct();
  }
}