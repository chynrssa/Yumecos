package com.example.project_tamt

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log // Import untuk Logcat

// --- Komentar impor OkHttp karena tidak akan digunakan untuk simulasi ---
// import okhttp3.*
// import okhttp3.MediaType.Companion.toMediaTypeOrNull
// import okhttp3.RequestBody.Companion.asRequestBody

import java.io.File // <--- UNCOMMENT OR ADD THIS LINE
import java.io.FileOutputStream
import java.io.IOException

class UploadBuktiActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private val PERMISSION_REQUEST_CODE = 100
    private lateinit var ivBuktiPembayaranPreview: ImageView
    private var selectedImageUri: Uri? = null // Menyimpan URI gambar yang dipilih

    // EditTexts
    private lateinit var etNomorOrder: EditText
    private lateinit var etMetodePembayaran: EditText
    private lateinit var etTanggalPembayaran: EditText
    private lateinit var etJumlahNominal: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.up_bukti_selanjutnya)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.upload_bukti_root_scroll_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi Views
        ivBuktiPembayaranPreview = findViewById(R.id.iv_bukti_pembayaran_preview)
        val btnKonfirmasi: Button = findViewById(R.id.btn_konfirmasi_upload)
        val btnUploadImageArea: LinearLayout = findViewById(R.id.btn_upload_image_area)

        etNomorOrder = findViewById(R.id.et_nomor_order)
        etMetodePembayaran = findViewById(R.id.et_metode_pembayaran)
        etTanggalPembayaran = findViewById(R.id.et_tanggal_pembayaran)
        etJumlahNominal = findViewById(R.id.et_jumlah_nominal)

        btnUploadImageArea.setOnClickListener {
            checkPermissionsAndOpenImageChooser()
        }

        btnKonfirmasi.setOnClickListener {
            // Panggil fungsi untuk mengupload data dan gambar (SEKARANG DISIMULASIKAN SAJA)
            simulateUploadSuccess()
        }

        val btnBack: ImageView = findViewById(R.id.btn_back_upload)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun checkPermissionsAndOpenImageChooser() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_REQUEST_CODE)
        } else {
            openImageChooser()
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageChooser()
            } else {
                Toast.makeText(this, "Izin akses penyimpanan ditolak. Tidak dapat mengunggah gambar.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data // Simpan URI gambar yang dipilih
            try {
                ivBuktiPembayaranPreview.setImageURI(selectedImageUri)
                ivBuktiPembayaranPreview.scaleType = ImageView.ScaleType.FIT_CENTER
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Gagal memuat gambar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- FUNGSI BARU UNTUK SIMULASI UPLOAD SUKSES ---
    private fun simulateUploadSuccess() {
        // 1. Ambil data dari EditTexts (tetap lakukan validasi input)
        val nomorOrder = etNomorOrder.text.toString().trim()
        val metodePembayaran = etMetodePembayaran.text.toString().trim()
        val tanggalPembayaran = etTanggalPembayaran.text.toString().trim()
        val jumlahNominal = etJumlahNominal.text.toString().trim()

        // 2. Lakukan validasi dasar (sama seperti sebelumnya)
        if (nomorOrder.isEmpty() || metodePembayaran.isEmpty() || tanggalPembayaran.isEmpty() || jumlahNominal.isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua data form.", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Mohon upload bukti pembayaran.", Toast.LENGTH_SHORT).show()
            return
        }

        // --- INI ADALAH BAGIAN SIMULASI SUKSES ---
        // Jika validasi lolos, kita langsung anggap upload berhasil
        Log.d("UploadBukti", "Simulasi Upload: Semua data valid. Menampilkan popup sukses.")
        Toast.makeText(this, "Upload berhasil (simulasi)!", Toast.LENGTH_SHORT).show()

        // Panggil popup sukses setelah "upload" berhasil disimulasikan
        showPaymentSuccessPopup()
    }

    // --- Helper function untuk mendapatkan File dari Uri (Tetap dipertahankan jika nanti ingin benar-benar upload) ---
    private fun getFileFromUri(uri: Uri): File? {
        val fileName = contentResolver.getFileName(uri)
        if (fileName == null) {
            Log.e("UploadBukti", "Gagal mendapatkan nama file dari URI: $uri")
            return null
        }

        val cacheDir = applicationContext.cacheDir
        val file = File(cacheDir, fileName)

        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            Log.e("UploadBukti", "Error saat menyalin file dari URI ke cache: ${e.message}", e)
            return null
        }
        return file
    }

    // Helper extension function untuk mendapatkan nama file dari Uri
    private fun android.content.ContentResolver.getFileName(uri: Uri): String? {
        var name: String? = null
        try {
            val cursor = query(uri, arrayOf(android.provider.OpenableColumns.DISPLAY_NAME), null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    name = it.getString(it.getColumnIndexOrThrow(android.provider.OpenableColumns.DISPLAY_NAME))
                }
            }
        } catch (e: Exception) {
            Log.e("UploadBukti", "Error saat mendapatkan nama file dari URI: ${e.message}", e)
        }
        return name
    }

    // --- Fungsi untuk menampilkan popup "Pembayaran Berhasil" (Tidak Berubah) ---
    private fun showPaymentSuccessPopup() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_pembayaran_berhasil, null)

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnOk: Button = dialogView.findViewById(R.id.btn_ok_popup)
        btnOk.setOnClickListener {
            alertDialog.dismiss()

            // Setelah popup ditutup, navigasi ke BuktiPembayaranActivity
            val intent = Intent(this, BuktiPembayaranActivity::class.java)
            // Kirim data yang diinput ke BuktiPembayaranActivity
            selectedImageUri?.let { uri ->
                intent.putExtra("uploaded_image_uri", uri.toString())
            }
            intent.putExtra("nomor_order", etNomorOrder.text.toString())
            intent.putExtra("metode_pembayaran", etMetodePembayaran.text.toString())
            intent.putExtra("tanggal_pembayaran", etTanggalPembayaran.text.toString())
            intent.putExtra("jumlah_nominal", etJumlahNominal.text.toString())

            startActivity(intent)
            finish() // Menutup UploadBuktiActivity
        }

        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)

        alertDialog.show()
    }
}