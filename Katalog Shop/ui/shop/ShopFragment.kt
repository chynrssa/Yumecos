package com.example.tampilanshop.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tampilanshop.R // Digunakan untuk ID aksi
import com.example.tampilanshop.databinding.FragmentShopBinding // Binding untuk ShopFragment

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBaru.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_baru) // Menggunakan ID aksi baru dari ShopFragment
        }

        binding.btnPopuler.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_popular) // Menggunakan ID aksi baru dari ShopFragment
        }

        binding.btnCewek.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_cewek) // Menggunakan ID aksi baru dari ShopFragment
        }

        binding.btnCowok.setOnClickListener {
            findNavController().navigate(R.id.action_shop_to_cowok) // Menggunakan ID aksi baru dari ShopFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}