package com.example.yumecos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yumecos.R
import com.example.yumecos.model.Costume

class CostumeAdapter(
    private val items: List<Costume>,
    private val onClick: (Costume) -> Unit
) : RecyclerView.Adapter<CostumeAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val costumeName: TextView = itemView.findViewById(R.id.costumeName)
        private val costumeOrigin: TextView = itemView.findViewById(R.id.costumeOrigin)
        private val costumePrice: TextView = itemView.findViewById(R.id.costumePrice)
        private val costumeThumb: ImageView = itemView.findViewById(R.id.costumeThumb)
        private val ratingText: TextView = itemView.findViewById(R.id.tvRating)

        fun bind(costume: Costume) {
            costumeName.text = costume.name
            costumeOrigin.text = costume.origin
            // Menggunakan properti baru 'priceRange'
            // Anda bisa menampilkan ini atau versi singkatnya di sini
            costumePrice.text = costume.priceRange
            costumeThumb.setImageResource(costume.thumbnail)

            val stars = getStarString(costume.rating)
            ratingText.text = "$stars (${costume.ratingCount})"

            itemView.setOnClickListener { onClick(costume) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        // Pastikan nama layout item ini benar
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_costume, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    private fun getStarString(rating: Float): String {
        val fullStars = rating.toInt()
        val halfStar = if (rating - fullStars >= 0.5f) 1 else 0
        val emptyStars = 5 - fullStars - halfStar
        return "★".repeat(fullStars) + "☆".repeat(emptyStars)
    }
}