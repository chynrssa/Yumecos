package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.yumecos.databinding.LayoutBinding // Pastikan nama ini cocok dengan file XML Anda (layout.xml -> LayoutBinding)

class ShopFragment : Fragment() {

    // Gunakan _binding agar bisa di-null-kan di onDestroyView untuk mencegah memory leak
    private var _binding: LayoutBinding? = null
    // Properti ini hanya valid antara onCreateView dan onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Meng-inflate (membuat) tampilan dari file XML dan menghubungkannya dengan ViewBinding
        // Ini adalah cara yang benar untuk Fragment, menggantikan setContentView.
        _binding = LayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Semua interaksi dengan view dilakukan di sini, setelah tampilan dipastikan sudah dibuat.

        // Mengakses view melalui 'binding' lebih aman daripada findViewById
        // dan akan memberikan error saat kompilasi jika ID tidak ditemukan.
        binding.textViewKosong.paint.isAntiAlias = false
        binding.buttonBelanja.paint.isAntiAlias = false

        // Menambahkan listener untuk tombol "Yuk, Belanja"
        binding.buttonBelanja.setOnClickListener {
            // Gunakan requireActivity() untuk mendapatkan konteks Activity
            // yang menjadi "rumah" bagi Fragment ini saat membuat Intent.
            val intent = Intent(requireActivity(), CartFilledActivity::class.java)
            startActivity(intent)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Membersihkan referensi binding saat view dihancurkan.
        // Ini adalah langkah penting untuk mencegah memory leak di Fragment.
        _binding = null
    }
}
