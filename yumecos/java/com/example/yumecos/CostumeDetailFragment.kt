package com.example.yumecos

// import android.content.Intent // <-- Dihapus, karena tidak lagi digunakan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yumecos.adapter.CarouselAdapter
import com.example.yumecos.databinding.FragmentCostumeDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class CostumeDetailFragment : Fragment() {

    private var _binding: FragmentCostumeDetailBinding? = null
    private val binding get() = _binding!!

    private val args: CostumeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCostumeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val costume = args.costumeData

        // Mengisi UI dengan data kostum
        binding.tvPriceRange.text = costume.priceRange
        binding.tvSetName.text = "MC SET: ${costume.name.uppercase()}"
        binding.tvRentalPeriod.text = costume.rentalPeriod
        binding.tvDescriptionContent.text = costume.description.replace("\\n", "\n")


        // Setup Carousel Gambar
        val imageAdapter = CarouselAdapter(costume.productImages)
        binding.productImagesCarousel.adapter = imageAdapter
        TabLayoutMediator(binding.productImagesIndicator, binding.productImagesCarousel) { _, _ -> }.attach()

        // Setup Badge Ukuran
        binding.ivSizeBadge.setImageResource(costume.sizeBadge)

        // Menambahkan fungsi klik untuk tombol kembali yang baru
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // --- BAGIAN YANG DISESUAIKAN ---
        // Menggunakan NavController untuk memanggil action dari nav_graph
        binding.btnRentNow.setOnClickListener {
            // 1. Dapatkan referensi ke action yang sudah kita buat di nav_graph.xml
            val action = CostumeDetailFragmentDirections.actionCostumeDetailFragmentToSyaratKetentuanActivity()
            binding.btnAddToCart.setOnClickListener {
                // Gunakan NavController untuk berpindah ke ID tujuan di nav_graph.xml
                findNavController().navigate(R.id.navigation_shop)
            }
            // 2. Perintahkan NavController untuk menjalankan action tersebut
            findNavController().navigate(action)
        }
        // --- AKHIR BAGIAN YANG DISESUAIKAN ---
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}