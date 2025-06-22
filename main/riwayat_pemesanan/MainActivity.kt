// MainActivity.kt

package com.example.riwayat_pemesanan

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Mengubah layout yang ditampilkan menjadi activity_main.xml (jika ada)
        // Atau langsung memulai SelesaiActivity
        // setContentView(R.layout.activity_main) // Jika Anda punya layout utama
        // ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //     val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //     v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //     insets
        // }

        // Langsung memulai SelesaiActivity
        val intent = Intent(this, RiwayatPemesananActivity::class.java)
        startActivity(intent)
        finish() // Menutup MainActivity agar tidak bisa kembali ke sini dengan tombol back
    }
}