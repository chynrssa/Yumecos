package com.example.yumecos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yumecos.R

class CarouselAdapter(private val bannerImages: List<Int>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    /**
     * ViewHolder bertugas untuk memegang referensi ke view di dalam setiap item carousel,
     * dalam kasus ini, hanya sebuah ImageView.
     */
    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerImageView: ImageView = itemView.findViewById(R.id.iv_banner_image)
    }

    /**
     * Fungsi ini dipanggil untuk membuat ViewHolder baru.
     * Ia akan "menggembungkan" (inflate) layout item_carousel_banner.xml.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carousel_banner, parent, false)
        return CarouselViewHolder(view)
    }

    /**
     * Fungsi ini menghubungkan data (gambar) pada posisi tertentu dengan ViewHolder.
     */
    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bannerImageView.setImageResource(bannerImages[position])
    }

    /**
     * Fungsi ini mengembalikan jumlah total item di dalam carousel.
     */
    override fun getItemCount(): Int {
        return bannerImages.size
    }
}