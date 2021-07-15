package com.example.stoicreminder

import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        val textview = findViewById<TextView>(R.id.textView6)
        textview.textSize = 25F
        textview.textAlignment = TEXT_ALIGNMENT_CENTER
        val text = assets.open("q.json")
        val qs = JSONObject(text.reader().readText())
        var mp = MediaPlayer()
        btn1.setOnClickListener {
            if (!mp.isPlaying) {
                val sound = randomSound()
                val textInd = sound.slice(1 until sound.length - 4).toInt()
                val quote: String = qs.getJSONArray("quotes")[textInd - 1] as String
                mp = playSound(sound)
                textview.text = quote
            } else {mp.stop()}
        }

    }

    private fun randomSound(): String {
        val sounds: MutableList<String> = mutableListOf()
        assets.list("quotes")?.forEach {
            sounds.add(it)
        }
        return sounds.random()
    }
    private fun playSound(sound: String): MediaPlayer {
        val file = assets.openFd("quotes/$sound")
        val mp = MediaPlayer()
        mp.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        mp.prepare()
        mp.start()
        return mp
    }
}




