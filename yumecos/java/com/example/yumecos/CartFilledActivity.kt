package com.example.yumecos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yumecos.databinding.ActivityCartFilledBinding
import java.text.NumberFormat
import java.util.Locale

// Asumsikan CartItem dan OnCartItemActionListener sudah didefinisikan di file terpisah
// karena error "Redeclaration" menunjukkan adanya duplikasi.

class CartFilledActivity : AppCompatActivity(), OnCartItemActionListener {

    // Menggunakan ViewBinding untuk akses yang lebih aman dan bersih
    private lateinit var binding: ActivityCartFilledBinding
    private lateinit var adapter: CartItemAdapter
    private lateinit var cartItems: MutableList<CartItem>

    private val SHIPPING_COST = 10000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Meng-inflate layout menggunakan ViewBinding
        binding = ActivityCartFilledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Memanggil fungsi-fungsi untuk setup
        initializeData()
        setupRecyclerView()
        setupListeners() // Panggil setup listeners di sini
        updateCartSummary()
    }

    // --- FUNGSI-FUNGSI INI ADALAH MEMBER CLASS, BUKAN LOCAL FUNCTION ---
    // Mereka berada di luar onCreate(), sehingga boleh menggunakan 'private'

    private fun initializeData() {
        // Pemanggilan constructor tidak perlu diubah karena menggunakan argumen posisional
        cartItems = mutableListOf(
            CartItem(getString(R.string.item_clara_name), getString(R.string.item_clara_desc_game), 130000.0, 1, getString(R.string.size_l), getString(R.string.tag_game)),
            CartItem(getString(R.string.item_fuxuan_name), getString(R.string.item_fuxuan_desc_game), 160000.0, 1, getString(R.string.size_l), getString(R.string.tag_game)),
            CartItem(getString(R.string.item_sylus_name), getString(R.string.item_sylus_desc_game), 110000.0, 1, getString(R.string.size_xl), getString(R.string.tag_game))
        )
    }

    private fun setupRecyclerView() {
        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(this)
        adapter = CartItemAdapter(cartItems, this)
        binding.cartItemsRecyclerview.adapter = adapter
    }

    private fun setupListeners() {
        // Handle tombol checkout untuk mengarahkan ke halaman Syarat & Ketentuan
        binding.buttonCheckout.setOnClickListener {
            val intent = Intent(this, SyaratKetentuanActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onQuantityChanged(position: Int, newQuantity: Int) {
        val item = cartItems[position]
        item.quantity = newQuantity
        adapter.updateItem(position)
        updateCartSummary()
    }

    private fun updateCartSummary() {
        // Menggunakan metode getTotalPrice() dari data class, yang lebih bersih
        // dan secara otomatis menggunakan 'pricePerUnit' yang baru.
        val subtotal = cartItems.sumOf { it.getTotalPrice() }
        val total = subtotal + SHIPPING_COST

        binding.subtotalValue.text = formatRupiah(subtotal)
        binding.shippingValue.text = formatRupiah(SHIPPING_COST)
        binding.totalValue.text = formatRupiah(total)
    }

    private fun formatRupiah(amount: Double): String {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        formatRupiah.maximumFractionDigits = 0
        return formatRupiah.format(amount)
    }
}
