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
        
        // إظهار واجهة التطبيق الرئيسية
        setContentView(R.layout.activity_main)

        // ربط زر التشغيل
        btnPlayRadio = findViewById(R.id.btnPlayRadio)

        // رابط البث المباشر لإذاعة القرآن الكريم
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
        mediaPlayer = MediaPlayer().apply {
            try {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                prepareAsync()
                
                setOnPreparedListener { mp ->
                    mp.start()
                    isPlaying = true
                    btnPlayRadio.text = "إيقاف الإذاعة"
                    Toast.makeText(this@MainActivity, "جاري تشغيل إذاعة القرآن الكريم...", Toast.LENGTH_SHORT).show()
                }

                setOnErrorListener { _, _, _ ->
                    Toast.makeText(this@MainActivity, "تعذر تشغيل الإذاعة، تأكد من الإنترنت", Toast.LENGTH_SHORT).show()
                    true
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopRadio() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
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
