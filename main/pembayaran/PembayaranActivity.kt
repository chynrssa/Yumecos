package com.example.project_tamt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.ImageView // Import ImageView

class PembayaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pembayaran) // Menggunakan layout pembayaran.xml

        // Terapkan insets ke root ScrollView dari pembayaran.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pembayaran_root_scroll_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Dapatkan referensi tombol "Upload bukti pembayaran"
        val btnUpload: Button = findViewById(R.id.btn_upload_pembayaran)

        // Atur OnClickListener untuk tombol "Upload bukti pembayaran"
        btnUpload.setOnClickListener {
            // Ketika tombol diklik, arahkan ke UploadBuktiActivity
            val intent = Intent(this, UploadBuktiActivity::class.java)
            startActivity(intent)
            // Anda bisa memilih untuk tidak memanggil finish() di sini
            // jika Anda ingin user bisa kembali ke halaman pembayaran ini dengan tombol back.
            // finish() // Opsional: tutup PembayaranActivity
        }

        // Listener untuk tombol back di header (jika Anda ingin berfungsi)
        val btnBack: ImageView = findViewById(R.id.btn_back_pembayaran)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Kembali ke activity sebelumnya
        }
    }
}