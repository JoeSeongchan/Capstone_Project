package com.android.chatver5.activity.login;

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
import com.android.chatver5.activity.chat.ChatGroupActivity;
import com.android.chatver5.activity.lifecyclerobserver.ServerDbLifeCycleManager;
import com.android.chatver5.databinding.ActivityLoginBinding;
import com.android.chatver5.db.data.User;
import com.android.chatver5.db.firedb.ServerDb;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity {


  private EditText etEmail, etPwd;
  private FirebaseAuth authDb;
  private ServerDb<User> userServerDb;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setUi();
    setDb();

    // 이미 로그인되어 있으면, 다음 Activity 로 전환.
    moveToNextAct();
  }

  private void setUi() {
    ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    TextView tvRegister = binding.tvRegisterLogin;
    Button btnDefaultLogin = binding.btnLoginLogin;
    etEmail = binding.etEmailLogin;
    etPwd = binding.etPwdLogin;

    // 회원 가입 버튼이 눌리면,
    tvRegister.setOnClickListener(this::signUp);

    //로그인 버튼이 눌리면,
    btnDefaultLogin.setOnClickListener(this::login);
  }

  private void setDb() {
    authDb = FirebaseAuth.getInstance();

    ServerDbLifeCycleManager serverDbLifeCycleManager = new ServerDbLifeCycleManager();
    getLifecycle().addObserver(serverDbLifeCycleManager);
    userServerDb = new ServerDb<>("user_list", User.class);
    serverDbLifeCycleManager.add(userServerDb);
  }

  private void signUp(@NonNull View view) {
    ActivityResultContract<Intent, String> contractForRegister =
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
    ActivityResultLauncher<Intent> launcherToRegister = registerForActivityResult(
        contractForRegister, email -> {
          if (email == null) {
            Utilities.log(LogType.d, "sign up fail.");
          } else {
            Utilities.log(LogType.d, "sign up success.");
            etEmail.setText(email);
          }
        });
    launcherToRegister.launch(new Intent(this, RegisterActivity.class));
  }

  private void login(@NonNull View view) {
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
    } else {
      Toast.makeText(this, "login success."
          + "\nid : " + currentUser.getUid()
          + "\nemail : " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(this, ChatGroupActivity.class);

      userServerDb.getData(currentUser.getUid())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(user -> {
            intent.putExtra("CUR_USER", user);
            startActivity(intent);
            finish();
          }, err -> Utilities.log(LogType.w, "err : " + err.getMessage()));
    }
  }
}