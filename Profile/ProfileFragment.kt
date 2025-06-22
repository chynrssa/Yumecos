package com.example.profile.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.profile.R
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cari tombol menu yang sudah kita tambahkan di XML
        val optionsMenuButton = view.findViewById<ImageButton>(R.id.options_menu_button)

        // Atur OnClickListener untuk tombol tersebut
        optionsMenuButton.setOnClickListener {
            showPopupMenu(it) // 'it' merujuk ke view tombol yang diklik
        }
    }

    private fun showPopupMenu(view: View) {
        // Buat instance PopupMenu
        val popupMenu = PopupMenu(requireContext(), view)

        // Inflate menu XML kita ke dalam PopupMenu
        popupMenu.inflate(R.menu.profile_options_menu)

        // Atur listener untuk item-item menu
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete_account -> {
                    showDeleteConfirmationDialog()
                    true
                }
                R.id.action_logout -> {
                    Toast.makeText(requireContext(), "Logout diklik", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Tampilkan PopupMenu
        popupMenu.show()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Yakin Menghapus Akun?")
            .setMessage("Akun tidak akan kembali bila dihapus, harap pertimbangkan")
            .setPositiveButton("Hapus Akun") { dialog, which ->
                // Lakukan logika penghapusan akun di sini
                Toast.makeText(requireContext(), "Akun dihapus!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null) // 'null' akan menutup dialog
            .setIcon(R.drawable.ic_delete_account) // Ikon opsional untuk dialog
            .show()
    }
}