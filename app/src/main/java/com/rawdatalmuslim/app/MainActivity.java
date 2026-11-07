package com.rawdatalmuslim;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPlayRadio = findViewById(R.id.btnPlayRadio);
        if (btnPlayRadio != null) {
            btnPlayRadio.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "مرحباً بك في تطبيق روضة المسلم", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
