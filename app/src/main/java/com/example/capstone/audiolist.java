package com.example.capstone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class audiolist extends AppCompatActivity {

    /**xml 변수*/
    ImageButton audioRecordImageBtn;
    TextView audioRecordText;

    /**오디오 파일 관련 변수*/

    // 오디오 파일 녹음 관련 변수
    private Uri audioUri = null; // 오디오 파일 uri

    // 이주엽 수정부분 111111111111111111
    private File audiofilespath =new File("data/user/0/com.example.capstone/app_recordDir");
    private File[] audiofiles = audiofilespath.listFiles();

    // 오디오 파일 재생 관련 변수
    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;
    ImageView playIcon;

    /** 리사이클러뷰 */
    private AudioAdapter audioAdapter;
    private ArrayList<Uri> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_list);

        permissionCheck();
        init();
    }


    // xml 변수 초기화
    // 리사이클러뷰 생성 및 클릭 이벤트
    private void init() {
        audioRecordImageBtn = findViewById(R.id.audioRecordImageBtn);
        audioRecordText = findViewById(R.id.audioRecordText);
        audioRecordImageBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), func_record.class);
                startActivity(intent);
            }
        });

        // 리사이클러뷰
        RecyclerView audioRecyclerView = findViewById(R.id.recyclerview);
        Log.d("수정한 부분", "수정했어");



        audioList = new ArrayList<>();
        audioAdapter = new AudioAdapter(this, audioList);

        // 이주엽 수정부분 22222222222222 리사이클러뷰 보여주기
        for (int i=1;i<audiofiles.length;i++){
            audioUri = Uri.parse(audiofiles[i].getAbsolutePath());
            audioList.add(audioUri);
            audioAdapter.notifyDataSetChanged();
        }


        audioRecyclerView.setAdapter(audioAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        audioRecyclerView.setLayoutManager(mLayoutManager);

        // 커스텀 이벤트 리스너 4. 액티비티에서 커스텀 리스너 객체 생성 및 전달
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnIconClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String uriName = String.valueOf(audioList.get(position));

                /*음성 녹화 파일에 대한 접근 변수 생성;
                     (ImageView)를 붙여줘서 View 객체를 형변환 시켜줌.
                     전역변수로 한 이유는
                    * */

                File file = new File(uriName);

                if(isPlaying){
                    // 음성 녹화 파일이 여러개를 클릭했을 때 재생중인 파일의 Icon을 비활성화(비 재생중)으로 바꾸기 위함.
                    if(playIcon == (ImageView)view){
                        // 같은 파일을 클릭했을 경우
                        stopAudio();
                    } else {
                        // 다른 음성 파일을 클릭했을 경우
                        // 기존의 재생중인 파일 중지
                        stopAudio();

                        // 새로 파일 재생하기
                        playIcon = (ImageView)view;
                        playAudio(file);
                    }
                } else {
                    playIcon = (ImageView)view;
                    playAudio(file);
                }
            }
        });
    }


    // 녹음 파일 재생
    private void playAudio(File file) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_audio_pause, null));
        isPlaying = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
            }
        });

    }

    // 녹음 파일 중지
    private void stopAudio() {
        playIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_audio_play, null));
        isPlaying = false;
        mediaPlayer.stop();
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
}










