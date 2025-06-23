package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.yumecos.databinding.ActivitySyaratKetentuanBinding
import com.example.yumecos.databinding.DialogSuccessBinding

class SyaratKetentuanActivity : AppCompatActivity() {

    // 1. Deklarasikan variabel untuk ViewBinding (menggantikan deklarasi manual)
    private lateinit var binding: ActivitySyaratKetentuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inflate layout menggunakan ViewBinding
        binding = ActivitySyaratKetentuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Logika awal: Tombol konfirmasi dinonaktifkan
        binding.buttonConfirm.isEnabled = false

        // 4. Listener untuk CheckBox menggunakan binding
        // (Untuk tampilan, lebih baik menggunakan drawable selector di XML)
        binding.checkboxAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.buttonConfirm.isEnabled = isChecked
        }

        // 5. Listener untuk Tombol Konfirmasi utama
        binding.buttonConfirm.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        // Menggunakan ViewBinding untuk layout dialog agar lebih aman
        val dialogBinding = DialogSuccessBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false) // Dialog tidak bisa ditutup dengan menekan di luar

        val successDialog = dialogBuilder.create()

        // Membuat background dialog transparan agar corner radius dari XML terlihat
        successDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Mengatur aksi untuk tombol "Lihat Pesanan" di dalam dialog
        dialogBinding.buttonConfirm.setOnClickListener {
            // Tutup dialog terlebih dahulu
            successDialog.dismiss()

            // Buat Intent untuk berpindah ke halaman Pembayaran
            // Pastikan Anda sudah membuat PaymentActivity dan mendaftarkannya di AndroidManifest.xml
            val intent = Intent(this, PembayaranActivity::class.java)
            startActivity(intent)
        }

        successDialog.show()
    }
}
