// RincianPembatalanActivity.kt
package com.example.yumecos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.util.Log

class RincianPembatalanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rincian_pembatalan)

        val orderId = intent.getStringExtra("ORDER_ID")

        // Inisialisasi Views
        val backButton: ImageView = findViewById(R.id.backButton)
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val rincianPembatalanTitle: TextView = findViewById(R.id.rincianPembatalanTitle)
        val tanggalPembatalan: TextView = findViewById(R.id.tanggalPembatalan)
        val statusPembatalan: TextView = findViewById(R.id.statusPembatalan)
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
        titleTextView.text = getString(R.string.header_title_riwayat_pemesanan) // Tetap Riwayat Pemesanan di header

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (orderId != null) {
            Log.d("RincianPembatalan", "Menerima ORDER_ID: $orderId")
            // Untuk demo, kita akan menggunakan data statis berdasarkan orderId
            when (orderId) {
                "1602" -> {
                    rincianPembatalanTitle.text = getString(R.string.title_rincian_pembatalan)
                    tanggalPembatalan.text = "Pada ${getString(R.string.default_date_time)}"
                    statusPembatalan.text = getString(R.string.pembatalan_berhasil)
                    productImage.setImageResource(R.drawable.claras)
                    productName.text = getString(R.string.product_name_clara)
                    productGame.text = getString(R.string.product_game_honkai_star_rail)
                    productSize.text = getString(R.string.ukuran_m)
                    productPrice.text = getString(R.string.price_clara)
                    valueDimintaOleh.text = "Clara User"
                    valueCustomer.text = "Clara Pembatal"
                    valueDimintaPada.text = getString(R.string.default_date_time)
                    valueAlasan.text = getString(R.string.alasan_pembatalan_example)
                }
                "1605" -> {
                    rincianPembatalanTitle.text = getString(R.string.title_rincian_pembatalan)
                    tanggalPembatalan.text = "Pada ${getString(R.string.default_date_time)}"
                    statusPembatalan.text = getString(R.string.pembatalan_berhasil)
                    productImage.setImageResource(R.drawable.march7th_hunt)
                    productName.text = getString(R.string.product_name_march7th_hunt)
                    productGame.text = getString(R.string.product_game_honkai_star_rail)
                    productSize.text = getString(R.string.ukuran_m)
                    productPrice.text = "RP 135.000 / 3 days" // Sesuaikan harga
                    valueDimintaOleh.text = "March 7th User"
                    valueCustomer.text = "March 7th Pembatal"
                    valueDimintaPada.text = getString(R.string.default_date_time)
                    valueAlasan.text = getString(R.string.alasan_pembatalan_example)
                }
                else -> {
                    // Data default jika orderId tidak ditemukan
                    rincianPembatalanTitle.text = "Rincian Pembatalan (Tidak Ditemukan)"
                    tanggalPembatalan.text = ""
                    statusPembatalan.text = "Data Tidak Ditemukan"
                    productImage.setImageResource(android.R.color.transparent)
                    productName.text = ""
                    productGame.text = ""
                    productSize.text = ""
                    productPrice.text = ""
                    valueDimintaOleh.text = ""
                    valueCustomer.text = ""
                    valueDimintaPada.text = ""
                    valueAlasan.text = ""
                    Log.w("RincianPembatalan", "ORDER_ID tidak ditemukan: $orderId")
                }
            }
        } else {
            Log.e("RincianPembatalan", "Tidak ada ORDER_ID yang diterima.")
            // Tampilkan pesan error atau kembali
        }
    }
}