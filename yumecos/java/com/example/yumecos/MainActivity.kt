package com.example.yumecos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.yumecos.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil NavHostFragment dari FragmentManager
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Ambil BottomNavigationView dari binding (id nav_view)
        val navView: BottomNavigationView = binding.navView

        // Kode di bawah ini berhubungan dengan ActionBar dan telah dinonaktifkan
        // untuk mencegah aplikasi crash setelah menggunakan tema NoActionBar.
        /*
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_order,
                R.id.navigation_shop,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        */

        // Hubungkan BottomNavigationView dengan NavController
        navView.setupWithNavController(navController)
    }

    // Fungsi ini juga berhubungan dengan ActionBar dan tidak lagi diperlukan.
    /*
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    */
}