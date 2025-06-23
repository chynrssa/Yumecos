package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yumecos.adapter.CostumeAdapter
import com.example.yumecos.databinding.FragmentHomeBinding
import com.example.yumecos.model.Costume

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data dummy yang sudah lengkap
        val data = getDummyCostumeData()

        // Setup RecyclerView dan navigasi saat item diklik
        binding.rvCatalog.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCatalog.adapter = CostumeAdapter(data) { costume ->
            // Handle item click: Pindah ke halaman detail sambil mengirim data costume
            val action = HomeFragmentDirections.actionHomeToDetail(costume)
            findNavController().navigate(action)
        }

        binding.btnShopNow.setOnClickListener {
            findNavController().navigate(R.id.navigation_shop)
        }
        // BENAR: Menggunakan NavController untuk berpindah Fragment
        binding.ivCart.setOnClickListener {
            // Cari NavController dan navigasikan ke ID tujuan
            findNavController().navigate(R.id.navigation_shop)
        }
        binding.etSearch.setOnClickListener {
            // Buat Intent untuk berpindah ke SearchActivity
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        // Search action
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // TODO: perform search
                true
            } else false
        }
    }

    // Fungsi untuk menyediakan data dummy yang lengkap
    private fun getDummyCostumeData(): List<Costume> {
        return listOf(
            Costume(
                id = 1,
                name = "Arlecchino",
                origin = "Genshin Impact",
                thumbnail = R.drawable.thumb_arlecchino,
                rating = 4.8f,
                ratingCount = 118,
                priceRange = "Rp 150.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "✨ Mc Night Flair Costume !\n💰 Harga sewa: Rp150.000 untuk 3 hari\n📦 Apa aja yang bisa kamu tambahin?\n• Wig cap: +Rp5.000\n• Boots kece: +Rp10.000\n📆 Mau sewa lebih lama? Bisa banget!\n• Tambah 1 hari (jadi 4 hari): +Rp42.000\n• Tambah 2 hari (jadi 5 hari): +Rp83.000\n• Tambah 4 hari (total 1 minggu): +Rp125.000\n📦 Udah termasuk laundry, jadi gak perlu cuci sendiri! Pakai, balikin, beres!",
                productImages = listOf(R.drawable.detail_arlecchino_1, R.drawable.detail_arlecchino_2),
                sizeBadge = R.drawable.ic_size_m_badge
            ),
            Costume(
                id = 2,
                name = "March 7th The Hunt",
                origin = "Honkai Star Rail",
                thumbnail = R.drawable.thumb_march,
                rating = 4.7f,
                ratingCount = 65,
                priceRange = "Rp 120.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "Versi The Hunt dari kostum March 7th, lengkap dengan ornamen dan detail.",
                productImages = listOf(R.drawable.detail_march7th_1,R.drawable.detail_march7th_2),
                sizeBadge = R.drawable.ic_size_m_badge
            ),
            Costume(
                id = 3,
                name = "Baizhi",
                origin = "Wuthering Waves",
                thumbnail = R.drawable.thumb_baizhi,
                rating = 4.9f,
                ratingCount = 120,
                priceRange = "Rp 130.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "Kostum Baizhi yang elegan dari game Wuthering Waves. Kualitas premium.",
                productImages = listOf(R.drawable.detail_baizhi_1,R.drawable.detail_baizhi_2),
                sizeBadge = R.drawable.ic_size_l_badge
            ),
            Costume(
                id = 4,
                name = "Ellen Joe",
                origin = "Zenless Zone Zero",
                thumbnail = R.drawable.thumb_ellen,
                rating = 4.8f,
                ratingCount = 118,
                priceRange = "Rp 140.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "Kostum Ellen Joe dari Zenless Zone Zero. Cocok untuk event dan photoshoot.",
                productImages = listOf(R.drawable.detail_ellen_1,R.drawable.detail_ellen_2),
                sizeBadge = R.drawable.ic_size_m_badge
            ),
            Costume(
                id = 5,
                name = "Fu Xuan",
                origin = "Honkai Star Rail",
                thumbnail = R.drawable.thumb_fuxuan,
                rating = 4.9f,
                ratingCount = 118,
                priceRange = "Rp 160.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "Kostum Master Diviner Fuxuan, detail akurat dan bahan berkualitas tinggi.",
                productImages = listOf(R.drawable.detail_fuxuan_1,R.drawable.detail_fuxuan_2),
                sizeBadge = R.drawable.ic_size_m_badge
            ),
            Costume(
                id = 6,
                name = "Clara",
                origin = "Honkai Star Rail",
                thumbnail = R.drawable.thumb_clara,
                rating = 4.7f,
                ratingCount = 135,
                priceRange = "Rp 135.000 / 3 hari",
                rentalPeriod = "3 Hari",
                description = "Satu set kostum Clara dan Svarog. Termasuk jaket merah ikonik.",
                productImages = listOf(R.drawable.detail_clara_1,R.drawable.detail_clara_2),
                sizeBadge = R.drawable.ic_size_s_badge
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}