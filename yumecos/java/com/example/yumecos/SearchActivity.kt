package com.example.yumecos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.util.Locale
import com.example.yumecos.databinding.SearchActivityBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: SearchActivityBinding

    private var mListMain = mutableListOf<LanguageData>()
    private var mListSearchResults = mutableListOf<ProductData>()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var languageAdapter: LanguageAdapter

    private var selectedCategories: List<String> = emptyList()
    private var selectedPriceRanges: List<String> = emptyList()
    private var selectedSizes: List<String> = emptyList()
    private var selectedRating: Int = 0

    private val filterActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val appliedFilters = data?.getBundleExtra("appliedFilters")
            appliedFilters?.let {
                selectedCategories = it.getStringArrayList("categories") ?: emptyList()
                selectedPriceRanges = it.getStringArrayList("priceRanges") ?: emptyList()
                selectedSizes = it.getStringArrayList("sizes") ?: emptyList()
                selectedRating = it.getInt("rating", 0)

                applyFiltersToSearchResults()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMainRecyclerView()
        initSearchResultsRecyclerView()
        setupData()
        setupProductData()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterSearchResults(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearchResults(newText)
                return true
            }
        })

        binding.backButton.setOnClickListener {
            binding.searchResultLayout.visibility = View.GONE
            binding.recyclerViewMain.visibility = View.VISIBLE
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            updateSortButtonUI(binding.btnTerkait)
        }

        binding.filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            filterActivityResultLauncher.launch(intent)
        }

        binding.btnTerbaru.setOnClickListener {
            sortProductsByLatest()
            updateSortButtonUI(binding.btnTerbaru)
        }

        binding.btnTerlaris.setOnClickListener {
            sortProductsByBestSelling()
            updateSortButtonUI(binding.btnTerlaris)
        }

        binding.btnTerkait.setOnClickListener {
            applyFiltersToSearchResults()
            updateSortButtonUI(binding.btnTerkait)
        }

        binding.recyclerViewMain.visibility = View.VISIBLE
        binding.searchResultLayout.visibility = View.GONE
    }

    private fun initMainRecyclerView() {
        binding.recyclerViewMain.setHasFixedSize(true)
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this, 2)
        languageAdapter = LanguageAdapter(mListMain)
        binding.recyclerViewMain.adapter = languageAdapter
    }

    private fun initSearchResultsRecyclerView() {
        binding.recyclerViewSearchResults.setHasFixedSize(true)
        binding.recyclerViewSearchResults.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter(mListSearchResults)
        binding.recyclerViewSearchResults.adapter = productAdapter
    }


    private fun setupData() {
        mListMain.add(LanguageData("Anime", R.drawable.anime))
        mListMain.add(LanguageData("Donghua", R.drawable.donghua))
        mListMain.add(LanguageData("Game", R.drawable.game))
        mListMain.add(LanguageData("Komik", R.drawable.komik))
        mListMain.add(LanguageData("Vtuber", R.drawable.vtuber))
        languageAdapter.setFilteredList(mListMain)
    }

    private fun setupProductData() {
        mListSearchResults.add(ProductData(
            name = "SYLUS",
            tema = "Love and DeepSpace",
            category = "Game",
            description = "Sylus relentless conqueror \uD83D\uDCB8 Rp. 150 Ribu / 3 hari \\nTambahan (additional): ...",
            rating = 4.5f,
            originalPrice = 350000,
            imageResId = R.drawable.sylus,
            isReady = true,
            size = "XL",
            addedDate = System.currentTimeMillis() - 86400000 * 3
        ))
        mListSearchResults.add(ProductData(
            name = "MC ENCHANTING VEIL",
            tema = "Love and DeepSpace",
            category = "Game",
            description = "Unlocks the Enchanting Veil costume for the Main Character, boosting charm and defense stats.",
            rating = 4.0f,
            originalPrice = 350000,
            imageResId = R.drawable.mc_enchanting_veil,
            isReady = false,
            size = "M-L",
            addedDate = System.currentTimeMillis() - 86400000 * 2
        ))
        mListSearchResults.add(ProductData(
            name = "MC SET: NIGHT FLAIR",
            tema = "Love and DeepSpace",
            category = "Game",
            description = "Complete Night Flair outfit set for the Main Character, offering unique combat bonuses.",
            rating = 5.0f,
            originalPrice = 400000,
            imageResId = R.drawable.mc_night_flair,
            isReady = true,
            size = "L",
            addedDate = System.currentTimeMillis() - 86400000 * 1
        ))
        mListSearchResults.add(ProductData(
            name = "XAVIER BUNNY",
            tema = "Love and DeepSpace",
            category = "Game",
            description = "Special Xavier Bunny companion with passive healing abilities and exclusive interaction options.",
            rating = 4.5f,
            originalPrice = 200000,
            imageResId = R.drawable.xavier_bunny,
            isReady = true,
            size = "M",
            addedDate = System.currentTimeMillis() - 86400000 * 4
        ))
        mListSearchResults.add(ProductData(
            name = "NICO ROBIN TIMESKIP",
            tema = "One Piece",
            category = "Anime",
            description = "Special Nico Robin companion with passive healing abilities and exclusive interaction options.",
            rating = 4.5f,
            originalPrice = 200000,
            imageResId = R.drawable.nico,
            isReady = true,
            size = "M",
            addedDate = System.currentTimeMillis() - 86400000 * 4
        ))
        mListSearchResults.add(ProductData(
            name = "ROBIN",
            tema = "Honkai Star Rail",
            category = "Game",
            description = "Special Robin companion with passive healing abilities and exclusive interaction options.",
            rating = 4.5f,
            originalPrice = 200000,
            imageResId = R.drawable.robin,
            isReady = true,
            size = "M-L",
            addedDate = System.currentTimeMillis() - 86400000 * 2
        ))
        mListSearchResults.add(ProductData(
            name = "MITSURI KANROJI",
            tema = "Demon Slayer",
            category = "Anime",
            description = "Special Mitsuri companion with passive healing abilities and exclusive interaction options.",
            rating = 4.5f,
            originalPrice = 250000,
            imageResId = R.drawable.mitsuri,
            isReady = true,
            size = "M-L",
            addedDate = System.currentTimeMillis() - 86400000 * 4
        ))
        mListSearchResults.add(ProductData(
            name = "ASA MITAKA",
            tema = "Chainsaw Man",
            category = "Anime",
            description = "Special Asa companion with passive healing abilities and exclusive interaction options.",
            rating = 4.5f,
            originalPrice = 200000-500000,
            imageResId = R.drawable.asa,
            isReady = true,
            size = "M",
            addedDate = System.currentTimeMillis() - 86400000 * 4
        ))
    }


    private fun filterSearchResults(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            mListSearchResults
        } else {
            mListSearchResults.filter { product ->
                product.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault())) ||
                        product.description.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault())) ||
                        product.tema.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
            }
        }

        productAdapter.setFilteredList(filteredList)
        // Corrected line: Update the result count TextView
        binding.resultCountTv.text = "${filteredList.size} Hasil Ditemukan"


        if (query.isNullOrEmpty()) {
            binding.searchResultLayout.visibility = View.GONE
            binding.recyclerViewMain.visibility = View.VISIBLE
        } else {
            binding.searchResultLayout.visibility = View.VISIBLE
            binding.recyclerViewMain.visibility = View.GONE
        }
    }

    private fun applyFiltersToSearchResults() {
        var filteredList = mListSearchResults.toMutableList()

        if (selectedCategories.isNotEmpty()) {
            filteredList = filteredList.filter { product ->
                selectedCategories.contains(product.category)
            }.toMutableList()
        }

        if (selectedPriceRanges.isNotEmpty()) {
            filteredList = filteredList.filter { product ->
                var matchesPriceRange = false
                for (range in selectedPriceRanges) {
                    matchesPriceRange = matchesPriceRange || when (range) {
                        getString(R.string.price_range_less_than_200k) -> product.originalPrice < 200000
                        getString(R.string.price_range_200k_to_500k) -> product.originalPrice >= 200000 && product.originalPrice <= 500000
                        getString(R.string.price_range_less_than_500k) -> product.originalPrice < 500000
                        getString(R.string.price_range_500k_to_1jt) -> product.originalPrice >= 500000 && product.originalPrice <= 1000000
                        getString(R.string.price_range_more_than_1jt) -> product.originalPrice > 1000000
                        else -> false
                    }
                }
                matchesPriceRange
            }.toMutableList()
        }

        if (selectedSizes.isNotEmpty()) {
            filteredList = filteredList.filter { product ->
                product.size != null && selectedSizes.contains(product.size)
            }.toMutableList()
        }

        if (selectedRating > 0) {
            filteredList = filteredList.filter { product ->
                product.rating >= selectedRating
            }.toMutableList()
        }

        productAdapter.setFilteredList(filteredList)
        binding.resultCountTv.text = "${filteredList.size} Hasil Ditemukan"
    }


    private fun sortProductsByLatest() {
        val sortedList = productAdapter.currentList.sortedByDescending { it.addedDate }
        productAdapter.setFilteredList(sortedList)
    }

    private fun sortProductsByBestSelling() {
        val sortedList = productAdapter.currentList.sortedByDescending { it.rating }
        productAdapter.setFilteredList(sortedList)
    }

    private fun updateSortButtonUI(selectedButton: MaterialButton) {
        val buttons = listOf(binding.btnTerbaru, binding.btnTerlaris, binding.btnTerkait)
        for (button in buttons) {
            if (button == selectedButton) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.pink_stroke))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
                button.setTextColor(ContextCompat.getColor(this, R.color.pink_stroke))
            }
        }
    }
}