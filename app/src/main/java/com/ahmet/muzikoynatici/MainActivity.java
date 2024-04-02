package com.ahmet.muzikoynatici;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView play, next, prev;
    TextView songName;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;

    Runnable runnable;
    int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.imgPlay);
        next = findViewById(R.id.imgNext);
        prev = findViewById(R.id.imgPrev);
        songName = findViewById(R.id.txtSong);
        seekBar = findViewById(R.id.seekBar);

        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.ali_babanin_ciftligi);
        songs.add(1, R.raw.arkadasim_essek);
        songs.add(2, R.raw.ayi);
        songs.add(3, R.raw.tatli_domatesler);
        songs.add(4, R.raw.gunaydin_cocuklar);
        songs.add(5, R.raw.karpuz_adam);
        songs.add(6, R.raw.kirmizi_balik);
        songs.add(7, R.raw.mini_mini_bir_kus);


        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(index));


        play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        play.setImageResource(R.drawable.ic_play);
                    } else {
                        mediaPlayer.start();
                        play.setImageResource(R.drawable.ic_pause);
                    }
                    setSongName();
                }
            });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null){
                    play.setImageResource(R.drawable.ic_pause);
                }
                if (index<songs.size()-1){
                    index++;
                }else {
                    index=0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                mediaPlayer = mediaPlayer.create(getApplicationContext(),songs.get(index));
                mediaPlayer.start();
                setSongName();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null){
                    play.setImageResource(R.drawable.ic_pause);
                }
                if (index>0){
                    index--;
                }else {
                    index=songs.size() -1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                mediaPlayer = mediaPlayer.create(getApplicationContext(),songs.get(index));
                mediaPlayer.start();
                setSongName();
            }
        });


        }

        private void setSongName(){
            if (index == 0) {
                songName.setText("Ali Babanın Çiftliği");
            }
            if (index == 1) {
                songName.setText("Arkadaşım Eşşek");
            }
            if (index == 2) {
                songName.setText("Ayı");
            }
            if (index == 3) {
                songName.setText("Tatlı Domatesler");
            }
            if (index == 4) {
                songName.setText("Günaydın Çocuklar");
            }
            if (index == 5) {
                songName.setText("Karpuz Adam");
            }
            if (index == 6) {
                songName.setText("Kırmızı Balık");
            }
            if (index == 7) {
                songName.setText("Mini Mini Bir Kuş");
            }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                    seekBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null){
                    try {
                        if (mediaPlayer.isPlaying()){
                            Message message = new Message();
                            message.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak") Handler handler = new Handler(){
        @Override
        public void handleMessage (Message msg) {
            seekBar.setProgress(msg.what);
        }
    };


}