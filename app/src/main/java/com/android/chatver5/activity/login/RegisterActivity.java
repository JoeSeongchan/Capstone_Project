package com.android.chatver5.activity.login;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.android.chatver5.R;
import com.android.chatver5.activity.lifecyclerobserver.ServerDbLifeCycleManager;
import com.android.chatver5.databinding.ActivityRegisterBinding;
import com.android.chatver5.db.data.User;
import com.android.chatver5.db.firedb.ServerDb;
import com.android.chatver5.db.firedb.TransactionManager;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

  // 컴포넌트.
  EditText etNick, etName, etEmail, etPhone, etPwd, etPwdCheck;
  TextView tvBirthInput;
  Button btnRegister;

  // DB.
  private FirebaseAuth firebaseAuth;
  private ServerDb<User> userServerDb;
  private TransactionManager transactionManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    setUi();
    setDb();
    btnRegister.setOnClickListener(this::registerAccount);
    tvBirthInput.setOnClickListener(this::setBirthDate);
  }

  private void setUi() {
    ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
    etNick = binding.etNickRegister;
    etName = binding.etNameRegister;
    etEmail = binding.etEmailRegister;
    etPhone = binding.etPhoneRegister;
    tvBirthInput = binding.tvBirthInputRegister;
    etPwd = binding.etPwdRegister;
    etPwdCheck = binding.etPwdCheckRegister;
    btnRegister = binding.btnRegisterRegister;

    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle("회원 가입");
    // 홈 버튼, 뒤로 가기 버튼 활성화.
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setDisplayShowHomeEnabled(true);
  }

  // DB 설정하는 함수. (인증서버)
  private void setDb() {
    firebaseAuth = FirebaseAuth.getInstance();

    // TransactionManager.
    TransactionManager transactionManager = new TransactionManager();

    // ServerDb 설정.
    ServerDbLifeCycleManager serverDbLifeCycleManager = new ServerDbLifeCycleManager();
    getLifecycle().addObserver(serverDbLifeCycleManager);
    userServerDb = new ServerDb<>("user_list", User.class);
    serverDbLifeCycleManager.add(userServerDb);
  }

  private void setBirthDate(View view) {
    OnDateSetListener onDateSetListener = (datePicker, year, month, day) ->
        tvBirthInput.setText(getString(R.string.tv_birth_date_form, year, month, day));
    DatePickerDialog dialog = new DatePickerDialog(
        this,
        onDateSetListener,
        2021,
        11,
        15);
    dialog.show();
  }

  // 등록 버튼 클릭 리스너.
  private void registerAccount(@NonNull View view) {
    // 사용자가 입력한 정보 가져오기.
    String inputNick = etNick.getText().toString().trim();
    String inputName = etName.getText().toString().trim();
    String inputEmail = etEmail.getText().toString().trim();
    String inputPhone = etNick.getText().toString().trim();
    String inputPwd = etPwd.getText().toString().trim();
    String inputPwdCheck = etPwdCheck.getText().toString().trim();
    List<String> inputBirthStrList = Arrays
        .asList(tvBirthInput.getText().toString().trim().split("/"));
    Date inputBirthDate = new GregorianCalendar(
        Integer.parseInt(inputBirthStrList.get(0)),
        Integer.parseInt(inputBirthStrList.get(1)) - 1,
        Integer.parseInt(inputBirthStrList.get(2))).getTime();

    // 비밀번호 체크.
    if (inputPwd.equals(inputPwdCheck)) {
      Utilities.log(LogType.d, "등록 버튼 눌림" +
          "\nemail : " + inputEmail +
          "\npwd : " + inputPwd);
      // 로딩 띄움.
      ProgressDialog loadDialog = new ProgressDialog(RegisterActivity.this);
      loadDialog.setMessage("가입 중입니다...");
      loadDialog.show();

      // auth 서버에 새로운 계정 등록.
      firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPwd)
          .addOnSuccessListener(authResult -> {
            loadDialog.dismiss();

            FirebaseUser user = firebaseAuth.getCurrentUser();
            assert user != null;
            String userEmail = user.getEmail();
            assert userEmail != null;
            String userId = user.getUid();
            User newUserData = new User(
                inputName,
                userId,
                inputNick,
                userEmail,
                inputPhone,
                inputBirthDate);
            // user DB 에 사용자 정보 저장.
            transactionManager.run(transaction -> {
              userServerDb.setData(newUserData, transaction);
              return null;
            });
            // 회원 가입 화면 빠져나오기.
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(RegisterActivity.this,
                "회원가입에 성공하셨습니다.",
                Toast.LENGTH_SHORT)
                .show();
          }).addOnFailureListener(err -> {
        loadDialog.dismiss();
        Utilities.log(LogType.w, "error.\n" +
            err.getMessage());
      });
    } else {
      Toast.makeText(RegisterActivity.this,
          "비밀번호가 틀렸습니다. 다시 입력해 주세요.",
          Toast.LENGTH_SHORT)
          .show();
    }
  }

//  public boolean onSupportNavigateUp() {
//    onBackPressed();
//    ; // 뒤로가기 버튼이 눌렸을시
//    return super.onSupportNavigateUp(); // 뒤로가기 버튼
//  }
}