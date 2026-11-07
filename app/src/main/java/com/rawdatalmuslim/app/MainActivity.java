package com.rawdatalmuslim;

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
        setContentView(R.layout.activity_main);

        btnPlayRadio = findViewById(R.id.btnPlayRadio);
        final String radioUrl = "https://stream.radiojar.com/8s5u27wnv0quv";

        if (btnPlayRadio != null) {
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
    }

    private void startRadio(String url) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    isPlaying = true;
                    if (btnPlayRadio != null) {
                        btnPlayRadio.setText("إيقاف الإذاعة");
                    }
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
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPlaying = false;
        if (btnPlayRadio != null) {
            btnPlayRadio.setText("تشغيل الإذاعة");
        }
        Toast.makeText(MainActivity.this, "تم إيقاف الإذاعة", Toast.LENGTH_SHORT).show();
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
