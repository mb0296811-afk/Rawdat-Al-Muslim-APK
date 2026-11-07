package com.rawdata.muslim

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlayRadio = findViewById<Button>(R.id.btnPlayRadio)
        btnPlayRadio?.setOnClickListener {
            Toast.makeText(this, "مرحباً بك في تطبيق روضة المسلم", Toast.LENGTH_SHORT).show()
        }
    }
}
