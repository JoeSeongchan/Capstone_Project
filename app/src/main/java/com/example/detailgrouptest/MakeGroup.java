package com.example.detailgrouptest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MakeGroup extends AppCompatActivity {

    Button btn_selectDate, btn_selectStarttime, btn_selectEndtime, btn_age, btn_groupP;
    ImageButton btn_ageleft, btn_ageright, btn_groupPleft, btn_groupPrihgt;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView textDate;
    TextClock textStarttime, textEndtime;

    int age = 0;
    int onclick = 0;
    int groupP = 2;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        btn_selectDate = findViewById(R.id.selectDate);
        btn_selectStarttime = findViewById(R.id.selectStarttime);
        btn_selectEndtime = findViewById(R.id.selectEndtime);

        btn_age = findViewById(R.id.age);
        btn_groupP = findViewById(R.id.groupP);

        btn_ageleft = findViewById(R.id.age_left);
        btn_ageright = findViewById(R.id.age_right);
        btn_groupPleft = findViewById(R.id.groupP_left);
        btn_groupPrihgt = findViewById(R.id.groupP_right);

        btn_selectDate.setOnClickListener(this::onClick);
        btn_selectStarttime.setOnClickListener(this::onClick);
        btn_selectEndtime.setOnClickListener(this::onClick);
        btn_ageleft.setOnClickListener(this::onClick);
        btn_ageright.setOnClickListener(this::onClick);
        btn_groupPleft.setOnClickListener(this::onClick);
        btn_groupPrihgt.setOnClickListener(this::onClick);

    }

    public void onClick(View view){
        if(view == btn_selectDate){
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            btn_selectDate.setText(year+" / "+(month+1)+" / "+dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(view == btn_selectStarttime){
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR);
            int mMin = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btn_selectStarttime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    }, mHour, mMin, false);
            timePickerDialog.show();
        }

        if(view == btn_selectEndtime){
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR);
            int mMin = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btn_selectEndtime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }
                    }, mHour, mMin, false);
            timePickerDialog.show();
        }

        if(view == btn_ageleft){
            if(onclick == 1 && age < 10){
                btn_age.setText("나이 자유");
            }
            else if(age>0){
                age = age - 10;
                if(age>0)
                    btn_age.setText(age+"대");
                else
                    btn_age.setText("나이 자유");
            }
        }

        if(view == btn_ageright){
            if(age<90){
                age = age + 10;
                btn_age.setText(age+"대");
                onclick = 1;
            }
        }

        if(view == btn_groupPleft){
            if(groupP>2){
                groupP = groupP - 1;
                btn_groupP.setText(groupP+"명");
            }
        }

        if(view == btn_groupPrihgt){
            if(groupP<5){
                groupP = groupP + 1;
                btn_groupP.setText(groupP+"명");
            }
        }

    }
}
