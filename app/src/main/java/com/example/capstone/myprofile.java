package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class myprofile extends AppCompatActivity {
    private TextView username,userid,mainsinger,song,profile,profilename,evaluate,eval1,eval2,eval3,eval4,favoritesongs;
    private ImageView album;
    private ImageButton mike,musicplay,samplevoices,option;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);
        init();
        changeprofile();
        click();
    }

    private void changeprofile(){

    }


    private void init(){
        username=findViewById(R.id.username);
        userid=findViewById(R.id.userid);
        mainsinger=findViewById(R.id.mainsinger);
        song=findViewById(R.id.song);
        profile=findViewById(R.id.profile);
        profilename=findViewById(R.id.profilename);
        evaluate=findViewById(R.id.evaluate);
        eval1=findViewById(R.id.eval1c);
        eval2=findViewById(R.id.eval2c);
        eval3=findViewById(R.id.eval3c);
        eval4=findViewById(R.id.eval4c);
        favoritesongs=findViewById(R.id.favoritesongs);
        album=findViewById(R.id.album);
        mike=findViewById(R.id.mike);
        musicplay=findViewById(R.id.musicplay);
        samplevoices=findViewById(R.id.samplevoices);
        option=findViewById(R.id.option);
    }


    private void click(){
        mike.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), audiolist.class);
                startActivity(intent);
            }
        });

        option.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), option.class);
                startActivity(intent);
            }
        });







    }














}
