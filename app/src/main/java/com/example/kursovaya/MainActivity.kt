package com.example.kursovaya

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    var heads = 0
    var tails = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun throw_coin(view: View){
        val text_view = findViewById<TextView>(R.id.current_coin_status)
        val text_heads = findViewById<TextView>(R.id.text_heads_count)
        val text_tails = findViewById<TextView>(R.id.text_tails_count)
        val coin_status = (0..1).random()
        val text: String
        if (coin_status == 1) {
            text = "орел"
            heads = heads + 1
        }
        else{
            text = "решка"
            tails = tails + 1
        }
        text_view.text = text
        text_heads.text = "орлов: "+heads
        text_tails.text = "орлов: "+tails
    }
    fun reset_count(view: View){
        val text_heads = findViewById<TextView>(R.id.text_heads_count)
        val text_tails = findViewById<TextView>(R.id.text_tails_count)
        heads = 0
        tails = 0
        text_heads.text = "орлов: "+heads
        text_tails.text = "орлов: "+tails
    }
}