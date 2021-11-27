package com.example.detailgrouptest.DB.entity.creategroup;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import com.example.detailgrouptest.DB.entity.Party;
import com.example.detailgrouptest.DB.entity.Party.AgeDetail;
import com.example.detailgrouptest.DB.entity.Party.Gender;
import com.example.detailgrouptest.DB.entity.Party.Genre;
import com.example.detailgrouptest.R;
import com.example.detailgrouptest.databinding.ActivityCreateGroupBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;


public class CreateGroupActivity extends AppCompatActivity {

  private ActivityCreateGroupBinding binding = ActivityCreateGroupBinding
      .inflate(getLayoutInflater());
  private String hostId = "";
  private FirebaseFirestore fireDb;
  private String groupName = "";
  private List<Genre> genreList;
  private Gender gender;
  private int age = 0;
  private List<AgeDetail> ageDetail;
  private Date meetingDate;
  private boolean[] isError;
  private LocalTime startTime;
  private LocalTime endTime;
  private int memMax;
  private String karaokeId;
  private String karaokeName;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());

    TextInputEditText titleInputText = binding.createGroupEtGroupTitle;

    // 최대 글자 수 설정.
    binding.createGroupTilGroupTitle.setCounterEnabled(true);
    binding.createGroupTilGroupTitle.setCounterMaxLength(10);

    // db 설정.
    fireDb = FirebaseFirestore.getInstance();

    setUi();

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
    setGenreBtnClickListener();
    setGenderBtnClickListener();
    setAgeBtnClickListener();
    setCalendarClickListener();
    setTimeClickListener();
    setMemberMaxNumClickListener();
    setCreatePartyBtnClickListener();
    getKaraokeIdFromFormerActivity();
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

  private void setGenreBtnClickListener() {
    binding.createGroupBtnGenreFree.setOnClickListener(this::onClickGenreAll);

    binding.createGroupBtnGenreBalad.setOnClickListener(v -> {
      // 기존에 사용자가 버튼을 이미 클릭한 경우,
      if (binding.createGroupBtnGenreBalad.isSelected()) {
        // select 해제.
        binding.createGroupBtnGenreBalad.setSelected(false);
        // balad genre 제거.
        genreList.remove(Genre.BALAD);
      }
      // 사용자가 처음 버튼을 클릭한 경우
      else {
        // select 설정.
        binding.createGroupBtnGenreBalad.setSelected(true);
        // free genre 해제.
        genreList.remove(Genre.FREE);
        // balad genre 추가.
        genreList.add(Genre.BALAD);
      }
    });
    binding.createGroupBtnGenreHiphop.setOnClickListener(v -> {
      if (binding.createGroupBtnGenreHiphop.isSelected()) {
        binding.createGroupBtnGenreHiphop.setSelected(false);
        genreList.remove(Genre.HIPHOP);
      } else {
        binding.createGroupBtnGenreHiphop.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.HIPHOP);
      }
    });
    binding.createGroupBtnGenreJpop.setOnClickListener(v -> {
      if (binding.createGroupBtnGenreJpop.isSelected()) {
        binding.createGroupBtnGenreJpop.setSelected(false);
        genreList.remove(Genre.JPOP);
      } else {
        binding.createGroupBtnGenreJpop.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.JPOP);
      }
    });
    binding.createGroupBtnGenreOldSong.setOnClickListener(v -> {

      if (binding.createGroupBtnGenreOldSong.isSelected()) {
        binding.createGroupBtnGenreOldSong.setSelected(false);
        genreList.remove(Genre.OLD_SONG);
      } else {
        binding.createGroupBtnGenreOldSong.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.OLD_SONG);
      }
    });
    binding.createGroupBtnGenreNormalSong.setOnClickListener(v -> {
      if (binding.createGroupBtnGenreNormalSong.isSelected()) {
        binding.createGroupBtnGenreNormalSong.setSelected(false);
        genreList.remove(Genre.NORMAL_SONG);
      } else {
        binding.createGroupBtnGenreNormalSong.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.NORMAL_SONG);
      }
    });
    binding.createGroupBtnGenreOther.setOnClickListener(v -> {
      if (binding.createGroupBtnGenreOther.isSelected()) {
        binding.createGroupBtnGenreOther.setSelected(false);
        genreList.remove(Genre.OTHER);
      } else {
        binding.createGroupBtnGenreOther.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.OTHER);
      }
    });
    binding.createGroupBtnGenrePop.setOnClickListener(v -> {
      if (binding.createGroupBtnGenrePop.isSelected()) {
        binding.createGroupBtnGenrePop.setSelected(false);
        genreList.remove(Genre.POP);
      } else {
        binding.createGroupBtnGenrePop.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.POP);
      }
    });
    binding.createGroupBtnGenreTop.setOnClickListener(v -> {
      if (binding.createGroupBtnGenreTop.isSelected()) {
        binding.createGroupBtnGenreTop.setSelected(false);
        genreList.remove(Genre.TOP);
      } else {
        binding.createGroupBtnGenreTop.setSelected(true);
        genreList.remove(Genre.FREE);
        genreList.add(Genre.TOP);
      }
    });
  }

  private void setGenderBtnClickListener() {
    binding.createGroupBtnGenderFree.setOnClickListener(v -> {
      v.setSelected(true);
      binding.createGroupBtnGenderMale.setSelected(false);
      binding.createGroupBtnGenderFemale.setSelected(false);
      gender = Gender.FREE;
    });
    binding.createGroupBtnGenderMale.setOnClickListener(v -> {
      binding.createGroupBtnGenderFree.setSelected(false);
      v.setSelected(true);
      binding.createGroupBtnGenderFemale.setSelected(false);
      gender = Gender.MALE;
    });
    binding.createGroupBtnGenderFemale.setOnClickListener(v -> {
      binding.createGroupBtnGenderFree.setSelected(false);
      binding.createGroupBtnGenderMale.setSelected(false);
      v.setSelected(true);
      gender = Gender.FEMALE;
    });
  }

  private void setAgeBtnClickListener() {
    binding.createGroupBtnAgeLeft.setOnClickListener(v -> {
      if (age == 0) {
        binding.createGroupTvAge.setText("나이 자유");
      } else if (age > 0) {
        age -= 10;
        if (age > 0) {
          binding.createGroupTvAge
              .setText(getString(R.string.createGroup_tv_age, age));
        } else if (age == 0) {
          binding.createGroupTvAge.setText("나이 자유");
          binding.createGroupBtnAgeEarly.setSelected(false);
          binding.createGroupBtnAgeMid.setSelected(false);
          binding.createGroupBtnAgeLate.setSelected(false);
          ageDetail.clear();
        }
      }
    });
    binding.createGroupBtnAgeRight.setOnClickListener(v -> {
      if (age < 90) {
        age += 10;
        binding.createGroupTvAge.setText(getString(R.string.createGroup_tv_age, age));
      }
    });
    binding.createGroupBtnAgeEarly.setOnClickListener(v -> {
      if (age == 0) {
        return;
      }
      if (v.isSelected()) {
        v.setSelected(false);
        ageDetail.remove(AgeDetail.EARLY);
      } else {
        v.setSelected(true);
        ageDetail.add(AgeDetail.EARLY);
      }
    });
    binding.createGroupBtnAgeMid.setOnClickListener(v -> {
      if (age == 0) {
        return;
      }
      if (v.isSelected()) {
        v.setSelected(false);
        ageDetail.remove(AgeDetail.MID);
      } else {
        v.setSelected(true);
        ageDetail.add(AgeDetail.MID);
      }
    });
    binding.createGroupBtnAgeLate.setOnClickListener(v -> {
      if (age == 0) {
        return;
      }
      if (v.isSelected()) {
        v.setSelected(false);
        ageDetail.remove(AgeDetail.LATE);
      } else {
        v.setSelected(true);
        ageDetail.add(AgeDetail.LATE);
      }
    });
  }

  private void setCalendarClickListener() {
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
        (view, year, month, dayOfMonth) -> {
          binding.createGroupBtnSelectDate
              .setText(year + " / " + (month + 1) + " / " + dayOfMonth);
          meetingDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
          isError[1] = false;   //선택확인
        }, mYear, mMonth, mDay);
    datePickerDialog.show();
  }

  private void setTimeClickListener() {
    binding.createGroupBtnSelectStartTime.setOnClickListener(v -> {
      final Calendar c = Calendar.getInstance();
      int mHour = c.get(Calendar.HOUR);
      int mMin = c.get(Calendar.MINUTE);
      TimePickerDialog timePickerDialog =
          new TimePickerDialog(this,
              (view, hourOfDay, minute) -> {
                binding.createGroupBtnSelectStartTime
                    .setText(String.format("%02d:%02d", hourOfDay, minute));
                startTime = LocalTime.of(hourOfDay, minute);
                isError[2] = false;   //선택확인
              }, mHour, mMin, false);
      timePickerDialog.show();
    });
    binding.createGroupBtnSelectEndTime.setOnClickListener(v -> {
      final Calendar c = Calendar.getInstance();
      int mHour = c.get(Calendar.HOUR);
      int mMin = c.get(Calendar.MINUTE);

      TimePickerDialog timePickerDialog = new TimePickerDialog(this,
          new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              binding.createGroupBtnSelectEndTime
                  .setText(String.format("%02d:%02d", hourOfDay, minute));
              endTime = LocalTime.of(hourOfDay, minute);
              isError[3] = false;   //선택확인
            }
          }, mHour, mMin, false);
      timePickerDialog.show();
    });
  }

  private void setMemberMaxNumClickListener() {
    binding.createGroupBtnMemberNumLeft.setOnClickListener(v -> {
      if (memMax > 2) {
        memMax--;
        binding.createGroupTvMemMaxNum.setText(memMax + "명");
      }
    });
    binding.createGroupBtnMemberNumRight.setOnClickListener(v -> {
      if (memMax < 5) {
        memMax++;
        binding.createGroupTvMemMaxNum.setText(memMax + "명");
      }
    });
  }

  private void setCreatePartyBtnClickListener() {
    if (!groupName.isEmpty()) {
      isError[0] = false;
    }

    boolean isTotallyError = false;
    for (boolean b : isError) {
      isTotallyError = isTotallyError || b;
    }

    if (isTotallyError) {
      binding.errorMessage.setText("입력되지 않는 항목이 있습니다.");
    } else {
      binding.errorMessage.setText("모임 생성 중");
      writeNewGroup();
    }
  }

  private void getKaraokeIdFromFormerActivity() {
    karaokeId = Optional
        .ofNullable(getIntent().getExtras().getString("KARAOKE_ID"))
        .orElse("default_karaoke_id");
    karaokeName = Optional
        .ofNullable(getIntent().getExtras().getString("KARAOKE_NAME"))
        .orElse("default_karaoke_name");
  }

  private void writeNewGroup() {
    Party party = new Party(hostId,
        groupName,
        genreList,
        gender,
        age,
        ageDetail,
        karaokeId,
        karaokeName,
        meetingDate,
        0,
        memMax,
        startTime,
        endTime);

    String autoGeneratedId = fireDb.collection("party_list").document().getId();
    party.setPathId(autoGeneratedId);
    fireDb.collection("party_list")
        .document(party.getPathId())
        .set(party)
        .addOnSuccessListener(unused -> Log.d(TAG, "SUCCESS!"))
        .addOnFailureListener(e -> Log.w(TAG, "FAILURE : " + e.getMessage()));
  }

}