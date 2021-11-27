package com.example.detailgrouptest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.detailgrouptest.DB.entity.Party.Genre;
import com.example.detailgrouptest.databinding.ActivityCreateGroupBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;


public class CreateGroupActivity extends AppCompatActivity {

  private ActivityCreateGroupBinding binding;
  private FirebaseFirestore fireDb;
  private String groupName;
  private List<Genre> genreList;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityCreateGroupBinding.inflate(getLayoutInflater());
    TextInputEditText titleInputText = binding.createGroupEtGroupTitle;

    // 최대 글자 수 설정.
    binding.createGroupTilGroupTitle.setCounterEnabled(true);
    binding.createGroupTilGroupTitle.setCounterMaxLength(10);

    // db 설정.
    fireDb = FirebaseFirestore.getInstance();


  }

  private void setUi() {
    binding.createGroupEtGroupTitle.addTextChangedListener(new TextWatcher() {
      TextInputLayout layout = binding.createGroupTilGroupTitle;

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()) {
          layout.setError("모임명을 입력해주세요.");
        } else {
          groupName = s.toString();
          layout.setError(null); //에러 메시지를 지워주는 기능
        }
      }
    });
  }

  // '모든 장르' 버튼 클릭한 경우,
  private void onClickGenreAll(View view) {
    // 모든 버튼 초기화.
    binding.createGroupBtnGenreBalad.setSelected(false);
    binding.createGroupBtnGenreHiphop.setSelected(false);
    binding.createGroupBtnGenreJpop.setSelected(false);
    binding.createGroupBtnGenreOldSong.setSelected(false);
    binding.createGroupBtnGenreNormalSong.setSelected(false);
    binding.createGroupBtnGenreOther.setSelected(false);
    binding.createGroupBtnGenrePop.setSelected(false);
    binding.createGroupBtnGenreTop.setSelected(false);
    genreList.add(Genre.FREE);
  }

  private void setOtherGenreBtnClickListener() {
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreOther.setOnClickListener(v -> {
      binding.createGroupBtnGenreOther.setSelected(false);
      genreList.remove(Genre.OTHER);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      binding.createGroupBtnGenreTop.setSelected(false);
      genreList.remove(Genre.FREE);
      genreList.add(Genre.TOP);
    });


  }
}