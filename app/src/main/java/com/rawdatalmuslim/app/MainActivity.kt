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
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlayRadio = findViewById<Button>(R.id.btnPlayRadio)
        val btnOpenQuran = findViewById<Button>(R.id.btnOpenQuran)
        
        // رابط بث إذاعة القرآن الكريم
        val radioUrl = "https://stream.radiojar.com/8s5u27wnv0quv"

        // تحكم في زر الإذاعة تشغيل/إيقاف
        btnPlayRadio?.setOnClickListener {
            if (!isPlaying) {
                startRadio(radioUrl, btnPlayRadio)
            } else {
                stopRadio(btnPlayRadio)
            }
        }

        // زر المصحف (يمكنك ربطه بشاشة أخرى لاحقاً أو إظهار رسالة تفاعلية)
        btnOpenQuran?.setOnClickListener {
            Toast.makeText(this, "جاري فتح مصحف روضة المسلم...", Toast.LENGTH_SHORT).show()
            // هنا يمكنك فتح شاشة عرض المصحف أو PDF لاحقاً
        }
    }

    private fun startRadio(url: String, btn: Button) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { mp ->
                    mp.start()
                    isPlaying = true
                    btn.text = "⏸ إيقاف الإذاعة"
                    Toast.makeText(this@MainActivity, "جاري تشغيل إذاعة القرآن الكريم...", Toast.LENGTH_SHORT).show()
                }
                setOnErrorListener { _, _, _ ->
                    Toast.makeText(this@MainActivity, "تعذر تشغيل الإذاعة، تأكد من الاتصال بالإنترنت", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRadio(btn: Button) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
        isPlaying = false
        btn.text = "📻 تشغيل إذاعة القرآن الكريم"
        Toast.makeText(this, "تم إيقاف الإذاعة", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
