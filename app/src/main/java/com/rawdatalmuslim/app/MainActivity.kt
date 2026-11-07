package com.rawdatalmuslim

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var btnPlayRadio: Button
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayRadio = findViewById(R.id.btnPlayRadio)
        val radioUrl = "https://stream.radiojar.com/8s5u27wnv0quv"

        btnPlayRadio.setOnClickListener {
            if (!isPlaying) {
                startRadio(radioUrl)
            } else {
                stopRadio()
            }
        }
    }

    private fun startRadio(url: String) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                    isPlaying = true
                    btnPlayRadio.text = "إيقاف الإذاعة"
                    Toast.makeText(this@MainActivity, "جاري تشغيل إذاعة القرآن الكريم...", Toast.LENGTH_SHORT).show()
                }
                setOnErrorListener { _, _, _ ->
                    Toast.makeText(this@MainActivity, "تعذر تشغيل الإذاعة", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRadio() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
        isPlaying = false
        btnPlayRadio.text = "تشغيل الإذاعة"
        Toast.makeText(this, "تم إيقاف الإذاعة", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
