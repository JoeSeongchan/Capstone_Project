package com.example.detailgrouptest.DB.entity;


import androidx.appcompat.app.AppCompatActivity;

public class GroupInfoActivity extends AppCompatActivity {

//  private ActivityGroupInfoBinding binding = ActivityGroupInfoBinding
//      .inflate(getLayoutInflater());
//  private FirebaseFirestore fireDb;
//  private String GroupId = "";
//
//
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(binding.getRoot());
//
//    // db 설정.
//    fireDb = FirebaseFirestore.getInstance();
//
//    setUi();
//
//  }
//
//  private void setUi() {
//
//  }
//
//  private void readGroupInfo() {
//    Party party = new Party(hostId,
//        groupName,
//        genreList,
//        gender,
//        age,
//        ageDetail,
//        karaokeId,
//        karaokeName,
//        meetingDate,
//        0,
//        memMax,
//        startTime,
//        endTime);
//
//    fireDb.collection("party_list")
//        .document(GroupId)
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//          @Override
//          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//            if (task.isSuccessful()) {
//              DocumentSnapshot document = task.getResult();
//              if (document.exists()) {
//                Log.d(TAG, "DocumentSnapshot data : " + document.getData());
//              } else {
//                Log.d(TAG, "No such document");
//              }
//
//            }
//          })
//        });
//
//  }

}
