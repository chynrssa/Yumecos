package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yumecos.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listener untuk area pencarian
        // Pastikan EditText atau CardView untuk search bar di XML Anda memiliki ID "etSearch"
        binding.etSearch.setOnClickListener {
            // Buat Intent untuk berpindah ke SearchActivity
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        // --- Memanggil fungsi untuk mengatur semua listener tombol sewa ---
        setupSewaButtonListeners()

        // Listener untuk tombol filter lainnya
        binding.btnBaru.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_baru)
        }

        binding.btnPopuler.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_popular)
        }

        binding.btnCewek.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_cewek)
        }

        binding.btnCowok.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_cowok)
        }
    }

    /**
     * Mengatur OnClickListener untuk semua tombol "Sewa" (btnSewa1 sampai btnSewa12).
     */
    private fun setupSewaButtonListeners() {
        // 1. Buat daftar (List) yang berisi semua referensi tombol sewa dari binding
        val sewaButtons = listOf(
            binding.btnSewa1, binding.btnSewa2, binding.btnSewa3,
            binding.btnSewa4, binding.btnSewa5, binding.btnSewa6,
            binding.btnSewa7, binding.btnSewa8, binding.btnSewa9,
            binding.btnSewa10, binding.btnSewa11, binding.btnSewa12
        )

        // 2. Lakukan perulangan untuk setiap tombol di dalam daftar
        sewaButtons.forEach { button ->
            // 3. Berikan OnClickListener yang sama untuk setiap tombol
            button.setOnClickListener {
                // Sesuai permintaan, arahkan ke navigation_shop.
                // Biasanya, tombol ini akan mengarah ke halaman detail produk,
                // tapi kita ikuti permintaan untuk saat ini.
                findNavController().navigate(R.id.navigation_shop)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
