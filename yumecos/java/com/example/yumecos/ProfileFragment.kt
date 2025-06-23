package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.yumecos.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengisi data profil secara dinamis
        binding.profileName.text = "Gawr Gura"
        binding.profileUsername.text = "@gawrgawr (from code)"
        binding.profileImage.setImageResource(R.drawable.profile)

        // Menambahkan fungsi klik untuk tombol menu opsi (titik tiga)
        binding.optionsMenuButton.setOnClickListener { anchorView ->
            showProfileOptionsMenu(anchorView)
        }

        // --- BAGIAN YANG DITAMBAHKAN ---
        // Menambahkan listener untuk tombol/layout "Sudah Tiba"
        // Pastikan Anda sudah menambahkan ID "btn_riwayat_sudah_tiba" pada LinearLayout di XML
        binding.btnRiwayatSudahTiba.setOnClickListener {
            // Buat Intent untuk berpindah ke halaman RiwayatPemesananActivity
            val intent = Intent(requireActivity(), RiwayatPemesananActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Membuat dan menampilkan PopupMenu yang itemnya berasal dari
     * res/menu/profile_options_menu.xml
     */
    private fun showProfileOptionsMenu(anchorView: View) {
        val popupMenu = PopupMenu(requireContext(), anchorView)
        popupMenu.menuInflater.inflate(R.menu.profile_options_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete_account -> {
                    Toast.makeText(requireContext(), "Hapus Akun diklik", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_logout -> {
                    Toast.makeText(requireContext(), "Logout diklik", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
