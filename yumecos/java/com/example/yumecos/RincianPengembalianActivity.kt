// RincianPengembalianActivity.kt
package com.example.yumecos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.util.Log

class RincianPengembalianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rincian_pengembalian)

        val orderId = intent.getStringExtra("ORDER_ID")

        // Inisialisasi Views
        val backButton: ImageView = findViewById(R.id.backButton)
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val rincianPengembalianTitle: TextView = findViewById(R.id.rincianPengembalianTitle)
        val tanggalPengembalian: TextView = findViewById(R.id.tanggalPengembalian)
        val statusPengembalian: TextView = findViewById(R.id.statusPengembalian)
        val productImage: ImageView = findViewById(R.id.productImage)
        val productName: TextView = findViewById(R.id.productName)
        val productGame: TextView = findViewById(R.id.productGame)
        val productSize: TextView = findViewById(R.id.productSize)
        val productPrice: TextView = findViewById(R.id.productPrice)
        val valueDimintaOleh: TextView = findViewById(R.id.valueDimintaOleh)
        val valueCustomer: TextView = findViewById(R.id.valueCustomer)
        val valueDimintaPada: TextView = findViewById(R.id.valueDimintaPada)
        val valueAlasan: TextView = findViewById(R.id.valueAlasan)

        // Setel teks header
        titleTextView.text = getString(R.string.header_title_riwayat_pemesanan)

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (orderId != null) {
            Log.d("RincianPengembalian", "Menerima ORDER_ID: $orderId")
            // Untuk demo, kita akan menggunakan data statis berdasarkan orderId
            when (orderId) {
                "1603" -> {
                    rincianPengembalianTitle.text = getString(R.string.title_rincian_pengembalian)
                    tanggalPengembalian.text = "Pada ${getString(R.string.default_date_time)}"
                    statusPengembalian.text = getString(R.string.pengembalian_berhasil)
                    productImage.setImageResource(R.drawable.fuxuans)
                    productName.text = getString(R.string.product_name_fuxuan)
                    productGame.text = getString(R.string.product_game_honkai_star_rail)
                    productSize.text = getString(R.string.ukuran_m)
                    productPrice.text = getString(R.string.price_fuxuan)
                    valueDimintaOleh.text = "Fuxuan User"
                    valueCustomer.text = "Fuxuan Pengembali"
                    valueDimintaPada.text = getString(R.string.default_date_time)
                    valueAlasan.text = getString(R.string.alasan_pengembalian_example)
                }
                // Anda bisa tambahkan orderId lain jika ada
                else -> {
                    rincianPengembalianTitle.text = "Rincian Pengembalian (Tidak Ditemukan)"
                    tanggalPengembalian.text = ""
                    statusPengembalian.text = "Data Tidak Ditemukan"
                    productImage.setImageResource(android.R.color.transparent)
                    productName.text = ""
                    productGame.text = ""
                    productSize.text = ""
                    productPrice.text = ""
                    valueDimintaOleh.text = ""
                    valueCustomer.text = ""
                    valueDimintaPada.text = ""
                    valueAlasan.text = ""
                    Log.w("RincianPengembalian", "ORDER_ID tidak ditemukan: $orderId")
                }
            }
        } else {
            Log.e("RincianPengembalian", "Tidak ada ORDER_ID yang diterima.")
        }
    }
}