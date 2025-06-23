// RiwayatPemesananActivity.kt (Sebelumnya SelesaiActivity.kt)

package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView // Impor TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// RiwayatPemesananActivity mengimplementasikan OnPesananActionListener
class RiwayatPemesananActivity : AppCompatActivity(), OnPesananActionListener {

    private lateinit var recyclerViewPesanan: RecyclerView
    private lateinit var pesananAdapter: PesananAdapter
    private val daftarPesananLengkap = mutableListOf<Pesanan>()
    private val daftarPesananFilter = mutableListOf<Pesanan>()

    private lateinit var tabSelesai: Button
    private lateinit var tabDibatalkan: Button
    private lateinit var tabPengembalian: Button
    private lateinit var headerTitle: TextView // Tambahkan ini

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selesai) // Layout selesai.xml tetap digunakan untuk tampilan keseluruhan

        // Inisialisasi RecyclerView
        recyclerViewPesanan = findViewById(R.id.recyclerViewPesanan)
        recyclerViewPesanan.layoutManager = LinearLayoutManager(this)

        // Inisialisasi tombol tab
        tabSelesai = findViewById(R.id.tab_selesai)
        tabDibatalkan = findViewById(R.id.tab_dibatalkan)
        tabPengembalian = findViewById(R.id.tab_pengembalian)

        // Inisialisasi header title
        headerTitle = findViewById(R.id.header_title)


        // Isi daftarPesananLengkap dengan data dummy
        // Ini harus mencakup semua jenis status untuk demo
        daftarPesananLengkap.add(
            Pesanan(
                id = "1601",
                namaProduk = getString(R.string.product_name_arlecchino),
                namaGame = getString(R.string.product_game_genshin_impact),
                tanggal = getString(R.string.date_example),
                harga = getString(R.string.price_arlecchino),
                status = getString(R.string.status_selesai),
                imageResId = R.drawable.arlecchino
            )
        )
        daftarPesananLengkap.add(
            Pesanan(
                id = "1602",
                namaProduk = getString(R.string.product_name_clara),
                namaGame = getString(R.string.product_game_honkai_star_rail),
                tanggal = getString(R.string.date_example),
                harga = getString(R.string.price_clara),
                status = getString(R.string.status_dibatalkan), // Status Dibatalkan
                imageResId = R.drawable.claras
            )
        )
        daftarPesananLengkap.add(
            Pesanan(
                id = "1603",
                namaProduk = getString(R.string.product_name_fuxuan),
                namaGame = getString(R.string.product_game_honkai_star_rail),
                tanggal = getString(R.string.date_example),
                harga = getString(R.string.price_fuxuan),
                status = getString(R.string.status_pengembalian), // Status Pengembalian
                imageResId = R.drawable.fuxuans
            )
        )
        daftarPesananLengkap.add(
            Pesanan(
                id = "1604",
                namaProduk = getString(R.string.product_name_march7th_hunt),
                namaGame = getString(R.string.product_game_honkai_star_rail),
                tanggal = getString(R.string.date_example),
                harga = getString(R.string.price_march7th),
                status = getString(R.string.status_selesai),
                imageResId = R.drawable.march7th_hunt
            )
        )
        daftarPesananLengkap.add(
            Pesanan(
                id = "1605",
                namaProduk = "March 7th The Hunt", // Contoh lain dengan nama produk sedikit beda
                namaGame = getString(R.string.product_game_honkai_star_rail),
                tanggal = getString(R.string.date_example),
                harga = "RP 135.000 / 3 days",
                status = getString(R.string.status_dibatalkan), // Status Dibatalkan
                imageResId = R.drawable.march7th_hunt
            )
        )


        // Inisialisasi Adapter dengan daftar pesanan yang difilter
        pesananAdapter = PesananAdapter(daftarPesananFilter, this) // 'this' merujuk ke OnPesananActionListener
        recyclerViewPesanan.adapter = pesananAdapter

        // Set listener untuk tombol tab
        tabSelesai.setOnClickListener {
            Log.d("RiwayatPemesananActivity", "Tab Selesai diklik")
            filterPesananByStatus(getString(R.string.status_selesai))
            updateTabButtons(tabSelesai)
            headerTitle.text = getString(R.string.header_title_riwayat_pemesanan) // Kembali ke judul default
        }

        tabDibatalkan.setOnClickListener {
            Log.d("RiwayatPemesananActivity", "Tab Dibatalkan diklik")
            filterPesananByStatus(getString(R.string.status_dibatalkan))
            updateTabButtons(tabDibatalkan)
            headerTitle.text = getString(R.string.title_rincian_pembatalan) // Ganti judul header
        }

        tabPengembalian.setOnClickListener {
            Log.d("RiwayatPemesananActivity", "Tab Pengembalian diklik")
            filterPesananByStatus(getString(R.string.status_pengembalian))
            updateTabButtons(tabPengembalian)
            headerTitle.text = getString(R.string.title_rincian_pengembalian) // Ganti judul header
        }

        val headerBackButton: ImageView = findViewById(R.id.header_back_button)
        headerBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Secara default, tampilkan pesanan "Selesai" saat Activity dimulai
        filterPesananByStatus(getString(R.string.status_selesai))
        updateTabButtons(tabSelesai)
    }

    private fun filterPesananByStatus(status: String) {
        daftarPesananFilter.clear()
        val filteredList = daftarPesananLengkap.filter { it.status == status }
        daftarPesananFilter.addAll(filteredList)
        pesananAdapter.notifyDataSetChanged()
        Log.d("RiwayatPemesananActivity", "Filter untuk status '$status' diterapkan. Jumlah: ${daftarPesananFilter.size}")
    }

    private fun updateTabButtons(selectedButton: Button) {
        // Reset semua tombol ke tampilan unselected
        tabSelesai.setBackgroundResource(R.drawable.tab_button_unselected)
        tabSelesai.setTextColor(ContextCompat.getColor(this, R.color.text_color_inactive))

        tabDibatalkan.setBackgroundResource(R.drawable.tab_button_unselected)
        tabDibatalkan.setTextColor(ContextCompat.getColor(this, R.color.text_color_inactive))

        tabPengembalian.setBackgroundResource(R.drawable.tab_button_unselected)
        tabPengembalian.setTextColor(ContextCompat.getColor(this, R.color.text_color_inactive))

        // Set tombol yang dipilih ke tampilan selected
        selectedButton.setBackgroundResource(R.drawable.tab_button_selected)
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.text_color_active))
    }

    // Implementasi dari OnPesananActionListener
    override fun onLihatPenilaianClick(orderId: String) {
        Log.d("RiwayatPemesananActivity", "Tombol Lihat Penilaian diklik untuk Order ID: $orderId")
        val intent = Intent(this, LihatPenilaianActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
    }

    override fun onRincianPembatalanClick(orderId: String) {
        Log.d("RiwayatPemesananActivity", "Tombol Rincian Pembatalan diklik untuk Order ID: $orderId")
        val intent = Intent(this, RincianPembatalanActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
    }

    override fun onRincianPengembalianClick(orderId: String) {
        Log.d("RiwayatPemesananActivity", "Tombol Rincian Pengembalian diklik untuk Order ID: $orderId")
        val intent = Intent(this, RincianPengembalianActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
    }

    override fun onSewaLagiClick(orderId: String) {
        Log.d("RiwayatPemesananActivity", "Tombol Sewa Lagi diklik untuk Order ID: $orderId")
        // Implementasi untuk fungsionalitas "Sewa Lagi" (misalnya, arahkan ke halaman detail produk)
        // val intent = Intent(this, DetailProdukActivity::class.java)
        // intent.putExtra("PRODUCT_ID", orderId) // Atau ID produk yang sesuai
        // startActivity(intent)
    }
}