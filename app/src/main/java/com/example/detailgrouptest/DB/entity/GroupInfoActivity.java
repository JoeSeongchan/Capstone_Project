package com.example.detailgrouptest.DB.entity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.detailgrouptest.databinding.ActivityGroupInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupInfoActivity extends AppCompatActivity {

  private ActivityGroupInfoBinding binding = ActivityGroupInfoBinding
      .inflate(getLayoutInflater());
  private FirebaseFirestore fireDb;
  private String GroupId = "";


  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());

    // db 설정.
    fireDb = FirebaseFirestore.getInstance();

    setUi();

  }

  private void setUi() {

  }

  private void readGroupInfo() {
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

    fireDb.collection("party_list")
        .document(GroupId)
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              DocumentSnapshot document = task.getResult();
              if (document.exists()) {
                Log.d(TAG, "DocumentSnapshot data : " + document.getData());
              } else {
                Log.d(TAG, "No such document");
              }

            }
          })
        });

  }

}
