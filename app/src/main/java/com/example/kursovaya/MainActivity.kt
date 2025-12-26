package com.example.kursovaya

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    var heads = 0
    var tails = 0
    var last_coin_status = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val videoView = findViewById<VideoView>(R.id.coin_view)
        val uri1 = Uri.parse("android.resource://$packageName/${R.raw.head_up}")
        videoView.setVideoURI(uri1)
        videoView.seekTo(1)
    }

    fun start_throw_coin(view: View){
        val amount_of_flips = findViewById<EditText>(R.id.amount_of_flips)
        val flips_count_str = amount_of_flips.text.toString().trim()
        if (flips_count_str.isNotEmpty()){
            val flips_count = flips_count_str.toInt()
            throw_coin(flips_count,1)
        }
    }
    fun throw_coin(total_flips: Int, current_flip: Int){
        if (current_flip > total_flips) {return}
        val videoView = findViewById<VideoView>(R.id.coin_view)
        val text_heads = findViewById<TextView>(R.id.text_heads_count)
        val text_tails = findViewById<TextView>(R.id.text_tails_count)
        val coin_status = (0..1).random()
        var uri1: Uri
        var uri2: Uri

        if (last_coin_status == 1) {
            uri1 = Uri.parse("android.resource://$packageName/${R.raw.head_up}")
        } else {
            uri1 = Uri.parse("android.resource://$packageName/${R.raw.tail_up}")
        }

        if (coin_status == 1) {
            uri2 = Uri.parse("android.resource://$packageName/${R.raw.head_down}")
            heads = heads + 1
        } else {
            uri2 = Uri.parse("android.resource://$packageName/${R.raw.tail_down}")
            tails = tails + 1
        }

        last_coin_status = coin_status
        videoView.setVideoURI(uri1)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = false
            mp.start()
        }

        videoView.setOnCompletionListener {
            videoView.setVideoURI(uri2)
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = false
                mp.start()
            }
            videoView.setOnCompletionListener {
                text_heads.text = "орлов: $heads"
                text_tails.text = "решек: $tails"
                throw_coin(total_flips, current_flip + 1)
            }
        }
    }
    fun reset_count(view: View){
        val text_heads = findViewById<TextView>(R.id.text_heads_count)
        val text_tails = findViewById<TextView>(R.id.text_tails_count)
        heads = 0
        tails = 0
        text_heads.text = "орлов: $heads"
        text_tails.text = "решек: $tails"
    }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            val videoView = findViewById<VideoView>(R.id.coin_view)
            val uri1: Uri
            if (last_coin_status == 1){
                uri1 = Uri.parse("android.resource://$packageName/${R.raw.head_up}")
            }
            else{
                uri1 = Uri.parse("android.resource://$packageName/${R.raw.tail_up}")
            }
            videoView.setVideoURI(uri1)
            videoView.seekTo(1)
            videoView.pause()
        }
    }
    fun about_autors(view: View){
        val intent = Intent(this, about_autors::class.java)
        resultLauncher.launch(intent)
    }
}