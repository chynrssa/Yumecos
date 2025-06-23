package com.example.yumecos

import android.content.Intent // Import untuk Intent
import android.net.Uri // Meskipun tidak lagi dipakai untuk ImageView, tetap ada jika ada rencana di masa depan
import android.os.Bundle
import android.widget.Button // Import untuk Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BuktiPembayaranActivity : AppCompatActivity() {

    // ivUploadedImage DIHAPUS karena tidak ada di layout popup_bukti.xml sesuai permintaan Anda
    private lateinit var tvNoPesanan: TextView
    private lateinit var tvNamaPembeli: TextView
    private lateinit var tvTanggalPemesanan: TextView
    private lateinit var tvNoHp: TextView
    private lateinit var tvNomorOrder: TextView
    private lateinit var tvMetodePembayaran: TextView
    private lateinit var tvTanggalPembayaran: TextView
    private lateinit var tvProdukSewa: TextView
    private lateinit var tvTotalPembayaran: TextView
    private lateinit var btnBackBukti: ImageView
    private lateinit var btnSelesai: Button // Deklarasi untuk tombol "Selesai"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.popup_bukti)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout_bukti_pembayaran)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi Views dari popup_bukti.xml
        // Hapus: ivUploadedImage = findViewById(R.id.iv_uploaded_image_bukti)
        tvNoPesanan = findViewById(R.id.tv_no_pesanan_bukti)
        tvNamaPembeli = findViewById(R.id.tv_nama_pembeli_bukti)
        tvTanggalPemesanan = findViewById(R.id.tv_tanggal_pemesanan_bukti)
        tvNoHp = findViewById(R.id.tv_no_hp_bukti)
        tvNomorOrder = findViewById(R.id.tv_nomor_order_bukti)
        tvMetodePembayaran = findViewById(R.id.tv_metode_pembayaran_bukti)
        tvTanggalPembayaran = findViewById(R.id.tv_tanggal_pembayaran_bukti)
        tvProdukSewa = findViewById(R.id.tv_produk_sewa_bukti)
        tvTotalPembayaran = findViewById(R.id.tv_total_pembayaran_bukti)
        btnBackBukti = findViewById(R.id.btn_back_bukti)
        btnSelesai = findViewById(R.id.btn_selesai_bukti) // Inisialisasi tombol "Selesai"

        // Ambil data yang dikirim dari UploadBuktiActivity
        // Baris untuk imageUriString dihapus karena gambar tidak ditampilkan
        // val imageUriString = intent.getStringExtra("uploaded_image_uri")

        val inputNomorOrder = intent.getStringExtra("nomor_order")
        val inputMetodePembayaran = intent.getStringExtra("metode_pembayaran")
        val inputTanggalPembayaran = intent.getStringExtra("tanggal_pembayaran")
        val inputJumlahNominal = intent.getStringExtra("jumlah_nominal") // Pastikan ini tetap diterima jika Anda ingin menggunakannya untuk Total Pembayaran

        // Bagian yang sebelumnya menampilkan gambar (dihapus karena tidak ada ImageView di layout)
        // if (imageUriString != null) {
        //     val imageUri = Uri.parse(imageUriString)
        //     // ivUploadedImage.setImageURI(imageUri) // Baris ini dihapus
        //     // ivUploadedImage.scaleType = ImageView.ScaleType.FIT_CENTER // Baris ini dihapus
        // }

        // Isi TextViews dengan data yang diterima dari inputan user
        tvNomorOrder.text = inputNomorOrder ?: "-"
        tvMetodePembayaran.text = inputMetodePembayaran ?: "-"
        tvTanggalPembayaran.text = inputTanggalPembayaran ?: "-"

        // Contoh: Mengisi tvTotalPembayaran dengan data inputJumlahNominal
        // Sesuaikan format jika diperlukan, misalnya menambahkan "Rp" atau "/3 Hari"
        tvTotalPembayaran.text = "Rp${inputJumlahNominal ?: "0"}/3 Hari" // Contoh: Mengambil total dari input

        // Untuk data lain seperti No. Pesanan, Nama Pembeli, Tanggal Pemesanan, No. Handphone, Produk Sewa,
        // saat ini masih menggunakan nilai hardcode dari popup_bukti.xml Anda.
        // Jika Anda ingin ini dinamis, Anda perlu mengirimkannya dari UploadBuktiActivity
        // dan mengisi TextViews di sini (contoh: tvNoPesanan.text = intent.getStringExtra("no_pesanan") ?: "-").

        // Atur listener untuk tombol kembali (btnBackBukti)
        btnBackBukti.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Kembali ke activity sebelumnya
        }

        // Atur listener untuk tombol "Selesai" (btnSelesai)
        btnSelesai.setOnClickListener {
            // Arahkan ke halaman utama pembayaran (PembayaranActivity)
            val intent = Intent(this, PembayaranActivity::class.java)
            // Membersihkan tumpukan aktivitas agar PembayaranActivity menjadi satu-satunya di stack
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish() // Menutup BuktiPembayaranActivity agar tidak bisa kembali dengan tombol back
        }
    }
}