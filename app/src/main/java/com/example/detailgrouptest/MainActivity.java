package com.example.detailgrouptest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button test, test2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById(R.id.TESTHOME);
        test2 = findViewById(R.id.TEST2);
        test.setOnClickListener(this::onClick);
        test2.setOnClickListener(this::onClick);

    }

    public void onClick(View view){
        if(view == test){
            Intent intent = new Intent(getApplicationContext(), MakeGroup.class);
            startActivity(intent);
        }

        if(view == test2){
            Intent intent = new Intent(getApplicationContext(), GroupDetail.class);
            startActivity(intent);
        }
    }

}