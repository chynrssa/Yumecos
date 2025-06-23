package com.example.keranjang

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

import android.widget.Button
import android.widget.Toast
import android.widget.TextView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        // Mendapatkan referensi ke TextView "Keranjang anda kosong!!" dan menonaktifkan anti-aliasing
        val textViewKosong: TextView = findViewById(R.id.textView_kosong)
        textViewKosong.paint.isAntiAlias = false

        // Mendapatkan referensi ke Button "Yuk, Belanja"
        val buttonBelanja: Button = findViewById(R.id.button_belanja)
        // Menonaktifkan anti-aliasing untuk Button ini
        buttonBelanja.paint.isAntiAlias = false

        // Mengatur OnClickListener untuk buttonBelanja
        buttonBelanja.setOnClickListener {
            // Membuat Intent untuk berpindah dari MainActivity ke CartFilledActivity
            val intent = Intent(this, CartFilledActivity::class.java)
            startActivity(intent) // Memulai Activity baru
            finish()
        }
    }
}