package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.flexbox.FlexboxLayout
import com.example.yumecos.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    // Menggunakan Set untuk menghindari duplikasi kategori/rentang harga/ukuran yang dipilih
    private val selectedCategories = mutableSetOf<String>()
    private val selectedPriceRange = mutableSetOf<String>()
    private val selectedSizes = mutableSetOf<String>()
    private var selectedRating: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data filter yang sudah diterapkan dari MainActivity jika ada
        val appliedFilters = intent.getBundleExtra("appliedFilters")
        appliedFilters?.let {
            selectedCategories.addAll(it.getStringArrayList("categories") ?: emptyList())
            selectedPriceRange.addAll(it.getStringArrayList("priceRanges") ?: emptyList())
            selectedSizes.addAll(it.getStringArrayList("sizes") ?: emptyList())
            selectedRating = it.getInt("rating", 0)
        }

        setupCategoryChips()
        setupPriceRangeChips()
        setupSizeChips()
        setupRatingStars() // Pastikan ini dipanggil

        // Update UI berdasarkan filter yang sudah ada (dari Bundle atau default kosong)
        updateChipSelectionUI()
        updateRatingStarsUI()

        binding.backButtonFilter.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.hapusFilterButton.setOnClickListener {
            resetFilters()
        }

        binding.terapkanFilterButton.setOnClickListener {
            applyFiltersAndReturnResult()
        }
    }

    private fun setupCategoryChips() {
        // Clear existing views to prevent duplication on re-creation if any
        binding.categoryChipsLayout.removeAllViews()

        val categories = listOf("Game", "Donghua", "Anime", "Vtuber", "Aksesoris", "Wig", "Props", "Lainnya")
        for (category in categories) {
            val chip = LayoutInflater.from(this).inflate(R.layout.chip_item, binding.categoryChipsLayout, false) as MaterialButton
            chip.text = category
            chip.setOnClickListener {
                if (selectedCategories.contains(category)) {
                    selectedCategories.remove(category)
                } else {
                    selectedCategories.add(category)
                }
                updateChipSelectionUI()
            }
            binding.categoryChipsLayout.addView(chip)
        }
        updateChipSelectionUI()
    }

    private fun setupPriceRangeChips() {
        binding.priceRangeChipsLayout.removeAllViews()

        val priceRanges = listOf(
            getString(R.string.price_range_less_than_200k),
            getString(R.string.price_range_200k_to_500k),
            getString(R.string.price_range_less_than_500k),
            getString(R.string.price_range_500k_to_1jt),
            getString(R.string.price_range_more_than_1jt)
        )
        for (range in priceRanges) {
            val chip = LayoutInflater.from(this).inflate(R.layout.chip_item, binding.priceRangeChipsLayout, false) as MaterialButton
            chip.text = range
            chip.setOnClickListener {
                if (selectedPriceRange.contains(range)) {
                    selectedPriceRange.remove(range)
                } else {
                    selectedPriceRange.add(range)
                }
                updateChipSelectionUI()
            }
            binding.priceRangeChipsLayout.addView(chip)
        }
    }

    private fun setupSizeChips() {
        binding.sizeChipsLayout.removeAllViews()

        val sizes = listOf("XS", "S", "M", "L", "XL", "XXL", "KIDS")
        for (size in sizes) {
            val chip = LayoutInflater.from(this).inflate(R.layout.chip_item, binding.sizeChipsLayout, false) as MaterialButton
            chip.text = size
            chip.setOnClickListener {
                if (selectedSizes.contains(size)) {
                    selectedSizes.remove(size)
                } else {
                    selectedSizes.add(size)
                }
                updateChipSelectionUI()
            }
            binding.sizeChipsLayout.addView(chip)
        }
    }

    private fun setupRatingStars() {
        val ratingStars = arrayOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )

        ratingStars.forEachIndexed { index, star ->
            star.setOnClickListener {
                selectedRating = index + 1 // Rating 1-5
                updateRatingStarsUI()
            }
        }
    }

    private fun updateChipSelectionUI() {
        // Update Category Chips
        for (i in 0 until binding.categoryChipsLayout.childCount) {
            val chip = binding.categoryChipsLayout.getChildAt(i) as? MaterialButton
            chip?.let {
                val category = it.text.toString()
                if (selectedCategories.contains(category)) {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.pink_stroke)
                    it.setTextColor(ContextCompat.getColor(this, R.color.white))
                } else {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
                    it.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                }
            }
        }

        // Update Price Range Chips
        for (i in 0 until binding.priceRangeChipsLayout.childCount) {
            val chip = binding.priceRangeChipsLayout.getChildAt(i) as? MaterialButton
            chip?.let {
                val priceRange = it.text.toString()
                if (selectedPriceRange.contains(priceRange)) {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.pink_stroke)
                    it.setTextColor(ContextCompat.getColor(this, R.color.white))
                } else {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
                    it.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                }
            }
        }

        // Update Size Chips
        for (i in 0 until binding.sizeChipsLayout.childCount) {
            val chip = binding.sizeChipsLayout.getChildAt(i) as? MaterialButton
            chip?.let {
                val size = it.text.toString()
                if (selectedSizes.contains(size)) {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, R.color.pink_stroke)
                    it.setTextColor(ContextCompat.getColor(this, R.color.white))
                } else {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.white)
                    it.setTextColor(ContextCompat.getColor(this, android.R.color.black))
                }
            }
        }
    }


    private fun updateRatingStarsUI() {
        val ratingStars = arrayOf(
            binding.star1,
            binding.star2,
            binding.star3,
            binding.star4,
            binding.star5
        )
        ratingStars.forEachIndexed { index, star ->
            if (index < selectedRating) {
                star.setImageResource(R.drawable.ic_star) // Bintang penuh
            } else {
                star.setImageResource(R.drawable.ic_star_empty) // Bintang kosong
            }
        }
    }

    private fun resetFilters() {
        selectedCategories.clear()
        selectedPriceRange.clear()
        selectedSizes.clear()
        selectedRating = 0
        updateChipSelectionUI()
        updateRatingStarsUI()
    }

    private fun applyFiltersAndReturnResult() {
        val resultIntent = Intent()
        val bundle = Bundle().apply {
            putStringArrayList("categories", ArrayList(selectedCategories))
            putStringArrayList("priceRanges", ArrayList(selectedPriceRange))
            putStringArrayList("sizes", ArrayList(selectedSizes))
            putInt("rating", selectedRating)
        }
        resultIntent.putExtra("appliedFilters", bundle)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}