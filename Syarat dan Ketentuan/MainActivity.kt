package com.example.syaratdanketentuan

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var checkboxAgree: CheckBox
    private lateinit var buttonConfirm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Views
        checkboxAgree = findViewById(R.id.checkbox_agree)
        buttonConfirm = findViewById(R.id.button_confirm)

        // Listener untuk CheckBox
        checkboxAgree.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Aktifkan Tombol
                buttonConfirm.isEnabled = true
                buttonConfirm.background = ContextCompat.getDrawable(this, R.drawable.button_background_enabled)
            } else {
                // Nonaktifkan Tombol
                buttonConfirm.isEnabled = false
                buttonConfirm.background = ContextCompat.getDrawable(this, R.drawable.button_background_disabled)
            }
        }

        // Listener untuk Tombol Konfirmasi
        buttonConfirm.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_success, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false) // Dialog tidak bisa ditutup dengan menekan di luar

        val successDialog = dialogBuilder.create()

        // Membuat background dialog transparan agar corner radius dari dialog_background.xml terlihat
        successDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val buttonViewOrder = dialogView.findViewById<Button>(R.id.button_confirm)
        buttonViewOrder.setOnClickListener {
            // Aksi saat tombol "Lihat Pesanan" diklik
            Toast.makeText(this, "Membuka halaman pesanan...", Toast.LENGTH_SHORT).show()
            successDialog.dismiss() // Tutup dialog
        }

        successDialog.show()
    }
}