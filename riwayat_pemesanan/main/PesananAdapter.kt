// PesananAdapter.kt

package com.example.riwayat_pemesanan

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat // Untuk warna teks dinamis
import androidx.recyclerview.widget.RecyclerView

// Interface untuk menangani klik tombol yang berbeda
interface OnPesananActionListener {
    fun onLihatPenilaianClick(orderId: String)
    fun onRincianPembatalanClick(orderId: String)
    fun onRincianPengembalianClick(orderId: String)
    fun onSewaLagiClick(orderId: String)
}

class PesananAdapter(
    private val daftarPesanan: List<Pesanan>,
    private val listener: OnPesananActionListener // Menggunakan interface yang lebih umum
) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewOrderIdBg: TextView = itemView.findViewById(R.id.item_order_id_bg)
        val imageViewItem: ImageView = itemView.findViewById(R.id.item_image)
        val textViewItemName: TextView = itemView.findViewById(R.id.item_name)
        val textViewItemGame: TextView = itemView.findViewById(R.id.item_game)
        val textViewItemDate: TextView = itemView.findViewById(R.id.item_date)
        val textViewItemPrice: TextView = itemView.findViewById(R.id.item_price)
        val textViewStatus: TextView = itemView.findViewById(R.id.item_status)
        val buttonSewaLagi: Button = itemView.findViewById(R.id.button_sewa_lagi)
        val buttonActionSecondary: Button = itemView.findViewById(R.id.button_action_secondary) // Tombol dinamis
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        return PesananViewHolder(view)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val pesanan = daftarPesanan[position]
        val context = holder.itemView.context

        holder.textViewOrderIdBg.text = context.getString(R.string.order_id_prefix) + pesanan.id
        holder.imageViewItem.setImageResource(pesanan.imageResId)
        holder.textViewItemName.text = pesanan.namaProduk
        holder.textViewItemGame.text = pesanan.namaGame
        holder.textViewItemDate.text = pesanan.tanggal
        holder.textViewItemPrice.text = pesanan.harga
        holder.textViewStatus.text = pesanan.status

        // Atur warna teks status
        when (pesanan.status) {
            context.getString(R.string.status_selesai) -> {
                holder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                // Konfigurasi tombol untuk status "Selesai"
                holder.buttonActionSecondary.text = context.getString(R.string.text_lihat_penilaian)
                holder.buttonActionSecondary.setOnClickListener {
                    listener.onLihatPenilaianClick(pesanan.id)
                }
            }
            context.getString(R.string.status_dibatalkan) -> {
                holder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.status_dibatalkan_text))
                // Konfigurasi tombol untuk status "Dibatalkan"
                holder.buttonActionSecondary.text = context.getString(R.string.text_rincian_pembatalan)
                holder.buttonActionSecondary.setOnClickListener {
                    listener.onRincianPembatalanClick(pesanan.id)
                }
            }
            context.getString(R.string.status_pengembalian) -> {
                holder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.status_pengembalian_text))
                // Konfigurasi tombol untuk status "Pengembalian"
                holder.buttonActionSecondary.text = context.getString(R.string.text_rincian_pengembalian)
                holder.buttonActionSecondary.setOnClickListener {
                    listener.onRincianPengembalianClick(pesanan.id)
                }
            }
            else -> {
                // Default atau status tidak dikenal
                holder.textViewStatus.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.buttonActionSecondary.text = "" // Sembunyikan atau atur teks default
                holder.buttonActionSecondary.setOnClickListener(null)
            }
        }

        // Mengatur OnClickListener untuk tombol "Sewa Lagi"
        holder.buttonSewaLagi.setOnClickListener {
            Log.d("PesananAdapter", "Tombol Sewa Lagi diklik untuk Order ID: ${pesanan.id}")
            listener.onSewaLagiClick(pesanan.id)
        }
    }

    override fun getItemCount(): Int {
        return daftarPesanan.size
    }
}