package com.example.rawdatalmuslim; // استبدل هذا باسم الحزمة الخاص بمشروعك (Package Name)

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlayRadio;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. هذا السطر هو المسؤول أساساً عن إظهار واجهة المصحف على شاشة الهاتف
        setContentView(R.layout.activity_main);

        // 2. ربط زر التشغيل (تأكد أن لديك زر في ملف الـ xml بهذا الـ ID أو قم بتعديله)
        btnPlayRadio = findViewById(R.id.btnPlayRadio);

        // رابط البث المباشر لإذاعة القرآن الكريم من القاهرة
        String radioUrl = "https://stream.radiojar.com/8s5u27wnv0quv";

        btnPlayRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    startRadio(radioUrl);
                } else {
                    stopRadio();
                }
            }
        });
    }

    private void startRadio(String url) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync(); // تجهيز البث أونلاين في الخلفية
            
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    isPlaying = true;
                    btnPlayRadio.setText("إيقاف الإذاعة");
                    Toast.makeText(MainActivity.this, "جاري تشغيل إذاعة القرآن الكريم...", Toast.LENGTH_SHORT).show();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(MainActivity.this, "تعذر تشغيل الإذاعة، تأكد من الإنترنت", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRadio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
            btnPlayRadio.setText("تشغيل الإذاعة");
            Toast.makeText(MainActivity.this, "تم إيقاف الإذاعة", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
