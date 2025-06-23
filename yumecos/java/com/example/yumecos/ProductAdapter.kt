package com.example.yumecos

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale
import com.example.yumecos.databinding.ItemProductBinding // Import generated binding class
import androidx.core.content.ContextCompat

class ProductAdapter(private var mList: List<ProductData>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Simpan daftar saat ini untuk diakses dari luar jika diperlukan (misal: di MainActivity untuk sorting)
    var currentList: List<ProductData> = mList
        private set // Hanya bisa di-set dari dalam kelas ini

    // Ubah ProductViewHolder untuk menggunakan View Binding
    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val productImage: ImageView = binding.productImage
        val productName: TextView = binding.productName
        val tema: TextView = binding.tema
        val category: TextView = binding.category
        val productDescription: TextView = binding.productDescription
        val originalPrice: TextView = binding.originalPrice
        val ratingLayout: LinearLayout = binding.ratingLayout
        val buttonReady: MaterialButton = binding.buttonReady
        val buttonXL: MaterialButton = binding.buttonXL
    }

    fun setFilteredList(mList: List<ProductData>) {
        this.mList = mList
        this.currentList = mList // Perbarui currentList juga
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Inflate layout menggunakan binding
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = mList[position]
        holder.productImage.setImageResource(currentItem.imageResId)
        holder.productName.text = currentItem.name
        holder.tema.text = currentItem.tema
        holder.productDescription.text = currentItem.description
        holder.category.text = currentItem.category

        // Logika untuk menampilkan harga (tanpa diskon)
        holder.originalPrice.text = currentItem.originalPrice.formatRupiah()
        holder.originalPrice.paintFlags = Paint.ANTI_ALIAS_FLAG // Pastikan tidak ada coretan
        holder.originalPrice.setTextColor(holder.itemView.context.resources.getColor(android.R.color.black))


        // Logika untuk menampilkan Rating Bintang
        holder.ratingLayout.removeAllViews() // Pastikan tidak ada bintang duplikat
        val fullStars = currentItem.rating.toInt()
        val hasHalfStar = currentItem.rating % 1 != 0f

        for (i in 0 until 5) {
            val star = ImageView(holder.itemView.context)
            // Menggunakan ukuran 16dp langsung, agar konsisten dengan item_product.xml jika tidak ada dimens.xml
            val starSizePx = (16 * holder.itemView.context.resources.displayMetrics.density).toInt()
            star.layoutParams = LinearLayout.LayoutParams(starSizePx, starSizePx)

            when {
                i < fullStars -> {
                    star.setImageResource(R.drawable.ic_star) // Bintang penuh
                }
                i == fullStars && hasHalfStar -> {
                    star.setImageResource(R.drawable.ic_star_half) // Bintang setengah
                }
                else -> {
                    star.setImageResource(R.drawable.ic_star_empty) // Bintang kosong
                }
            }
            holder.ratingLayout.addView(star) // Tambahkan bintang ke LinearLayout
        }

        // Logika untuk tombol "Ready"
        if (currentItem.isReady) {
            holder.buttonReady.visibility = View.VISIBLE
            holder.buttonReady.text = "Ready"
        } else {
            holder.buttonReady.visibility = View.GONE
        }

        // Logika untuk tombol "XL" (ukuran)
        if (!currentItem.size.isNullOrEmpty()) {
            holder.buttonXL.visibility = View.VISIBLE
            holder.buttonXL.text = currentItem.size
        } else {
            holder.buttonXL.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

// Extension function untuk format rupiah sederhana
fun Int.formatRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    val symbols = (formatter as java.text.DecimalFormat).decimalFormatSymbols
    symbols.currencySymbol = "Rp "
    formatter.decimalFormatSymbols = symbols
    formatter.maximumFractionDigits = 0 // Tidak ada desimal
    return formatter.format(this)
}