package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.database.groupdisplay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter {
    //리사이클러뷰에 넣을 데이터 리스트
    List<groupdisplay> group;
    Context context;
    String title;
    int timeattack,distance,member;
    String local;


    public GroupAdapter(Context context, List<groupdisplay> group){
        this.group = group;
        this.context = context;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        GroupAdapter.MyViewHolder viewHolder = new GroupAdapter.MyViewHolder(view);
        return viewHolder; }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        groupdisplay groupthis = group.get(position);


        String title = groupthis.getTitle();
        String local = groupthis.getLocal();
        int distance = groupthis.getDistance();
        int membermax = groupthis.getMax();
        int member = groupthis.getMember();
        long starttime = groupthis.getStarttime();
        long nowtime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
        Calendar startt = Calendar.getInstance();
        Calendar nowt = Calendar.getInstance();
        startt.set((int)(starttime/100000000),(int)(starttime % 100000000 / 1000000),(int)(starttime % 1000000 / 10000),(int)(starttime % 10000 / 100),(int)(starttime % 100 ));
        nowt.set((int)(nowtime/100000000),(int)(nowtime % 100000000 / 1000000),(int)(nowtime % 1000000 / 10000),(int)(nowtime % 10000 / 100),(int)(nowtime % 100 ));

        if(nowt.before(startt)) {
             long lefttime = (startt.getTimeInMillis()-nowt.getTimeInMillis())/1000;
             long leftday = lefttime/(24*60*60);
             long lefthour = lefttime%(24*60*60)/(60*60);
             long leftminute = lefttime%(60*60)/60;

             myViewHolder.timeattack.setText("※남은시간:"+leftday+"일 "+lefthour+"시 "+leftminute+"분");
        }

        else{myViewHolder.timeattack.setText("※모집 마감");};


        myViewHolder.title.setText(groupthis.getTitle());
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout moim;
        TextView title;
        TextView timeattack;

        //만드는데 시간이 걸릴듯한 기능들
        TextView distance;
        TextView members;
        TextView local;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moim = itemView.findViewById(R.id.moim);
            title = itemView.findViewById(R.id.moimtitle);
            local = itemView.findViewById(R.id.moimlocal);
            timeattack = itemView.findViewById(R.id.moimtimeattack);
            distance = itemView.findViewById(R.id.moimdistance);
            members = itemView.findViewById(R.id.moimmembers);

            moim.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }



    @Override public int getItemCount() { return group.size(); }



}
