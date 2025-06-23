package com.example.yumecos // Sesuaikan dengan package Anda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yumecos.model.Costume

class CatalogAdapter(
    private val costumeList: List<Costume>,
    private val onItemClick: (Costume) -> Unit
) : RecyclerView.Adapter<CatalogAdapter.CostumeViewHolder>() {

    inner class CostumeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val costumeThumb: ImageView = itemView.findViewById(R.id.costumeThumb)
        private val costumeName: TextView = itemView.findViewById(R.id.costumeName)
        private val costumeOrigin: TextView = itemView.findViewById(R.id.costumeOrigin)
        // Tambahkan ID lain jika ada, misal: tvRating, costumePrice

        fun bind(costume: Costume) {
            // Asumsi Anda punya satu gambar utama untuk thumbnail
            costumeThumb.setImageResource(costume.productImages.firstOrNull() ?: R.drawable.placeholder_image)
            costumeName.text = costume.name
            costumeOrigin.text = costume.origin

            itemView.setOnClickListener {
                onItemClick(costume)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostumeViewHolder {
        // Ganti R.layout.item_catalog dengan nama file layout item Anda yang benar
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalog, parent, false)
        return CostumeViewHolder(view)
    }

    override fun getItemCount(): Int = costumeList.size

    override fun onBindViewHolder(holder: CostumeViewHolder, position: Int) {
        holder.bind(costumeList[position])
    }
}