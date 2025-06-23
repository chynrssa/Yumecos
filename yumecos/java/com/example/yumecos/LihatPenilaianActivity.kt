// LihatPenilaianActivity.kt

package com.example.yumecos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.util.Log // Tambahkan import Log

class LihatPenilaianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lihat_penilaian)

        // 1. Ambil data dari Intent
        val orderId = intent.getStringExtra("ORDER_ID")
        // val reviewerName = intent.getStringExtra("REVIEWER_NAME") // Tidak digunakan karena data diambil berdasarkan orderId

        // 2. Temukan Views di layout lihat_penilaian.xml
        val reviewerNameTextView: TextView = findViewById(R.id.reviewerName)
        val rentalResponseContentTextView: TextView = findViewById(R.id.rentalResponseContent)
        val productNameTextView: TextView = findViewById(R.id.productName)
        val reviewerAvatarImageView: ImageView = findViewById(R.id.reviewerAvatar)
        val productImageImageView: ImageView = findViewById(R.id.productImage)
        val reviewDateTextView: TextView = findViewById(R.id.reviewDate)
        // Pastikan Anda juga menemukan dan mengatur ImageView untuk bintang rating jika ada di layout Anda
        // val star1: ImageView = findViewById(R.id.star1)
        // ... dst

        // Inisialisasi tombol kembali di header
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Kembali ke activity sebelumnya
        }

        // 3. Gunakan data yang diterima untuk mengisi Views
        if (orderId != null) {
            Log.d("LihatPenilaianActivity", "Menerima ORDER_ID: $orderId")
            // Berdasarkan orderId, Anda akan mengambil data penilaian dari database atau sumber data lain.
            // Untuk demonstrasi, saya akan mengisi data secara statis.
            when (orderId) {
                "1601" -> {
                    // Data untuk pesanan 1601 (Arlecchino)
                    reviewerNameTextView.text = "Cahyaaaaa7"
                    rentalResponseContentTextView.text = "Alhamdulillah, terima kasih banyak atas kepercayaannya. Kami sangat senang bisa melayani Anda. Semoga puas dengan layanan dan kostum yang disewa, dan kami berharap dapat menyambut Anda kembali di lain waktu. 😊"
                    productNameTextView.text = "Arlecchino Genshin impact"
                    reviewerAvatarImageView.setImageResource(R.drawable.poto_profile)
                    productImageImageView.setImageResource(R.drawable.arlecchino)
                    reviewDateTextView.text = "2025-01-19 10:20"
                    // Mengatur bintang rating (asumsi ada drawable ic_star_filled dan ic_star_empty)
                    // setStarRating(5) // Contoh: 5 bintang
                }
                "1602" -> {
                    // Data untuk pesanan 1602 (Clara)
                    reviewerNameTextView.text = "Budi Santoso"
                    rentalResponseContentTextView.text = "Kostumnya bagus, pengiriman cepat. Top! Sangat direkomendasikan!"
                    productNameTextView.text = "Clara Honkai Star Rail" // Menggunakan nama produk dari Clara
                    reviewerAvatarImageView.setImageResource(R.drawable.poto_profile) // Ganti jika ada avatar berbeda
                    productImageImageView.setImageResource(R.drawable.claras) // Menggunakan clara
                    reviewDateTextView.text = "2025-01-20 15:00"
                    // setStarRating(4) // Contoh: 4 bintang
                }
                // Anda dapat menambahkan case lain untuk orderId "1603" dan "1604" jika ingin menampilkan review untuk mereka
                else -> {
                    // Tangani jika orderId tidak ditemukan atau tidak valid
                    Log.w("LihatPenilaianActivity", "ORDER_ID tidak ditemukan atau tidak valid: $orderId")
                    reviewerNameTextView.text = "Data Tidak Ditemukan"
                    rentalResponseContentTextView.text = "Maaf, data penilaian untuk pesanan ini tidak tersedia."
                    productNameTextView.text = ""
                    reviewerAvatarImageView.setImageResource(android.R.color.transparent) // Atau placeholder
                    productImageImageView.setImageResource(android.R.color.transparent) // Atau placeholder
                    reviewDateTextView.text = ""
                    // Sembunyikan atau set placeholder lain
                }
            }
        } else {
            // Tangani jika tidak ada ORDER_ID yang dikirim
            Log.e("LihatPenilaianActivity", "Tidak ada ORDER_ID yang diterima dari Intent.")
            reviewerNameTextView.text = "Error"
            rentalResponseContentTextView.text = "Tidak ada ID pesanan yang diterima."
            productNameTextView.text = ""
            reviewerAvatarImageView.setImageResource(android.R.color.transparent)
            productImageImageView.setImageResource(android.R.color.transparent)
            reviewDateTextView.text = ""
        }
    }

    /*
    // Contoh fungsi untuk mengatur bintang rating jika Anda memiliki ImageView terpisah untuk setiap bintang
    private fun setStarRating(rating: Int) {
        val stars = listOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3),
            findViewById<ImageView>(R.id.star4),
            findViewById<ImageView>(R.id.star5)
        )
        for (i in stars.indices) {
            if (i < rating) {
                stars[i].setImageResource(R.drawable.ic_star_filled)
            } else {
                stars[i].setImageResource(R.drawable.ic_star_empty)
            }
        }
    }
    */
}