package com.example.yumecos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yumecos.databinding.EachItemBinding // Import generated binding class

class LanguageAdapter(private var mList: List<LanguageData>) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(private val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Akses view langsung dari binding
        val logo: ImageView = binding.logoIv
        val titleTv: TextView = binding.titleTv
    }

    fun setFilteredList(mList : List<LanguageData>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        // Inflate layout menggunakan binding
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentItem = mList[position]
        holder.logo.setImageResource(currentItem.logo)
        holder.titleTv.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}