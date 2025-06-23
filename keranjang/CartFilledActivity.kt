// CartFilledActivity.kt
package com.example.keranjang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale


class CartFilledActivity : AppCompatActivity(), OnCartItemActionListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartItemAdapter
    private lateinit var cartItems: MutableList<CartItem> // Daftar data item keranjang

    // TextViews untuk ringkasan (subtotal, shipping, total)
    private lateinit var subtotalValueTextView: TextView
    private lateinit var shippingValueTextView: TextView
    private lateinit var totalValueTextView: TextView

    private val SHIPPING_COST = 10000.0 // Biaya pengiriman tetap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_filled) // Pastikan ini mengacu ke layout yang benar

        cartItems = mutableListOf(
            CartItem(getString(R.string.item_clara_name), getString(R.string.item_clara_desc_game), 130000.0, 1, getString(R.string.size_l), getString(R.string.tag_game)),
            CartItem(getString(R.string.item_fuxuan_name), getString(R.string.item_fuxuan_desc_game), 160000.0, 1, getString(R.string.size_l), getString(R.string.tag_game)),
            CartItem(getString(R.string.item_sylus_name), getString(R.string.item_sylus_desc_game), 110000.0, 1, getString(R.string.size_xl), getString(R.string.tag_game))
        )

        // --- INISIALISASI RECYCLERVIEW ---
        // Temukan RecyclerView dari layout
        recyclerView = findViewById(R.id.cart_items_recyclerview)
        // Atur LayoutManager (misalnya, LinearLayoutManager untuk daftar vertikal)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Buat instance adapter dan lewati daftar item serta 'this' sebagai listener
        adapter = CartItemAdapter(cartItems, this)
        // Setel adapter ke RecyclerView
        recyclerView.adapter = adapter
        // --- AKHIR INISIALISASI RECYCLERVIEW ---


        // Inisialisasi TextViews untuk bagian ringkasan
        subtotalValueTextView = findViewById(R.id.subtotal_value)
        shippingValueTextView = findViewById(R.id.shipping_value)
        totalValueTextView = findViewById(R.id.total_value)

        // Lakukan perhitungan awal dan perbarui tampilan ringkasan
        updateCartSummary()

        // Handle tombol checkout
        val checkoutButton: Button = findViewById(R.id.button_checkout)
        checkoutButton.setOnClickListener {
            // Logika untuk proses checkout
            // Misalnya, tampilkan Toast atau navigasi ke layar pembayaran
            // Toast.makeText(this, "Proceeding to checkout!", Toast.LENGTH_SHORT).show()
        }
    }

    // --- Implementasi dari interface OnCartItemActionListener ---
    // Method ini akan dipanggil oleh CartItemAdapter ketika kuantitas suatu item berubah
    override fun onQuantityChanged(position: Int, newQuantity: Int) {
        val item = cartItems[position] // Dapatkan objek CartItem yang relevan dari daftar
        item.quantity = newQuantity    // Perbarui kuantitas di objek CartItem

        adapter.updateItem(position) // Beri tahu adapter bahwa item pada posisi ini telah berubah
        // Ini lebih efisien daripada menggambar ulang seluruh daftar
        updateCartSummary()          // Perbarui total keranjang karena ada perubahan kuantitas
    }

    // Fungsi untuk menghitung ulang dan memperbarui tampilan ringkasan keranjang
    private fun updateCartSummary() {
        var subtotal = 0.0
        for (item in cartItems) {
            subtotal += item.getTotalPrice() // Hitung total harga untuk setiap item
        }

        val total = subtotal + SHIPPING_COST // Hitung total keseluruhan

        // Perbarui TextViews dengan nilai yang sudah diformat
        subtotalValueTextView.text = formatRupiah(subtotal)
        shippingValueTextView.text = formatRupiah(SHIPPING_COST)
        totalValueTextView.text = formatRupiah(total)
    }

    // Fungsi utilitas untuk memformat jumlah uang ke format Rupiah
    private fun formatRupiah(amount: Double): String {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        if (amount % 1.0 == 0.0) { // Jika jumlahnya bulat, jangan tampilkan desimal
            formatRupiah.maximumFractionDigits = 0
        } else { // Jika ada desimal, tampilkan 2 digit
            formatRupiah.minimumFractionDigits = 2
            formatRupiah.maximumFractionDigits = 2
        }
        return formatRupiah.format(amount)
    }
}