package com.example.capstone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class upload_option extends AppCompatActivity {
    private TextView direct,record;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private String userid="IOxZt7qFPxdE93PNtElzSztmYpP2";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadaudio);
        init();
        click();
    }

    private void init() {
        direct = findViewById(R.id.audioupload);
        record = findViewById(R.id.recordupload);
    }

    private void click() {
        direct.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/mp3");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload, 1);
            }
        });




        record.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), func_record.class);
                startActivity(intent);
            }
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Uri fileUri = data.getData();
            StorageReference s_ref = storage.getReference();
            StorageReference r_ref = s_ref.child(userid+"/"+fileUri.getLastPathSegment());
            UploadTask uploadTask = r_ref.putFile((fileUri));

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(upload_option.this, "응안돼~", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }





}










