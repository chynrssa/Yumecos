// CartItemAdapter.kt
package com.example.keranjang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

// Interface untuk callback saat kuantitas berubah
// Ini adalah cara Adapter berkomunikasi kembali dengan Activity
interface OnCartItemActionListener {
    fun onQuantityChanged(position: Int, newQuantity: Int)
}

class CartItemAdapter(
    // Daftar item keranjang yang akan ditampilkan oleh adapter
    private val cartItems: MutableList<CartItem>,
    // Listener untuk menangani interaksi pengguna (misalnya, tombol plus/minus)
    private val listener: OnCartItemActionListener
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() { // Warisi dari RecyclerView.Adapter

    // Inner class yang mewakili satu baris/item di RecyclerView
    // Ini menampung referensi ke semua View dalam item_cart.xml
    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemDesc: TextView = itemView.findViewById(R.id.item_desc)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemSizeTag: Button = itemView.findViewById(R.id.item_size_tag)
        val itemGameTag: Button = itemView.findViewById(R.id.item_game_tag)
        val itemQty: TextView = itemView.findViewById(R.id.item_qty)
        val itemQtyPlus: Button = itemView.findViewById(R.id.item_qty_plus)
        val itemQtyMinus: Button = itemView.findViewById(R.id.item_qty_minus)

        // Blok init ini akan dieksekusi saat ViewHolder pertama kali dibuat
        init {
            itemQtyPlus.setOnClickListener {
                // Periksa apakah posisi item masih valid
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentQuantity = cartItems[position].quantity
                    // Panggil fungsi di listener (yaitu di CartFilledActivity)
                    listener.onQuantityChanged(position, currentQuantity + 1)
                }
            }

            itemQtyMinus.setOnClickListener {
                // Periksa apakah posisi item masih valid
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentQuantity = cartItems[position].quantity
                    // Pastikan kuantitas tidak kurang dari 1
                    if (currentQuantity > 1) {
                        // Panggil fungsi di listener (yaitu di CartFilledActivity)
                        listener.onQuantityChanged(position, currentQuantity - 1)
                    }
                    // Opsional: Jika kuantitas sudah 1 dan tombol minus ditekan lagi,
                    // Anda bisa memanggil listener.onItemRemoved(position) di sini
                }
            }
        }
    }

    // Dipanggil saat RecyclerView perlu membuat ViewHolder baru
    // Ini terjadi ketika tampilan baru perlu ditampilkan di layar (misalnya, saat menggulir)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        // Mengembang (inflate) layout item_cart.xml untuk setiap item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartItemViewHolder(view)
    }

    // Dipanggil oleh RecyclerView untuk menampilkan data pada ViewHolder tertentu
    // Ini adalah tempat Anda mengisi data dari objek CartItem ke dalam View yang sesuai
    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = cartItems[position] // Dapatkan objek CartItem dari daftar berdasarkan posisinya

        // Setel data ke elemen-elemen View di dalam ViewHolder
        holder.itemName.text = item.name
        holder.itemDesc.text = item.description
        // Format harga ke Rupiah
        holder.itemPrice.text = "${formatRupiah(item.pricePerUnit)} / 3 days"
        holder.itemSizeTag.text = item.size
        holder.itemGameTag.text = item.tag
        holder.itemQty.text = item.quantity.toString() // Setel kuantitas saat ini

        // Setel gambar item berdasarkan nama item (Anda perlu memiliki drawable ini)
        val imageResId = when (item.name) {
            "Clara" -> R.drawable.clara
            "Fuxuan" -> R.drawable.fuxuan
            "Sylus" -> R.drawable.sylus
            else -> R.drawable.placeholder_image // Pastikan Anda punya gambar placeholder
        }
        holder.itemImage.setImageResource(imageResId)
    }

    // Mengembalikan jumlah total item yang ada dalam daftar data
    override fun getItemCount(): Int = cartItems.size // Atau: return cartItems.size

    // --- Fungsi Bantuan ---

    // Fungsi utilitas untuk memformat jumlah uang ke format Rupiah
    private fun formatRupiah(amount: Double): String {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        // Atur jumlah digit desimal agar tidak menampilkan ,00 jika angka bulat
        if (amount % 1.0 == 0.0) {
            formatRupiah.maximumFractionDigits = 0
        } else {
            formatRupiah.minimumFractionDigits = 2
            formatRupiah.maximumFractionDigits = 2
        }
        return formatRupiah.format(amount)
    }

    // Fungsi untuk memperbarui seluruh data di adapter
    // Dipanggil saat data dasar berubah secara signifikan
    fun updateData(newItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newItems)
        notifyDataSetChanged() // Memberi tahu RecyclerView untuk menggambar ulang semua item
    }

    // Fungsi untuk memperbarui satu item tertentu di adapter
    // Lebih efisien daripada notifyDataSetChanged() jika hanya satu item yang berubah
    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }
}