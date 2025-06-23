package com.example.yumecos

data class ProductData(
    val name: String,
    val tema: String, // Tetap gunakan tema untuk detail spesifik
    val category: String, // <--- TAMBAHKAN FIELD BARU INI untuk kategori filter
    val description: String,
    val rating: Float,
    val originalPrice: Int,
    val imageResId: Int,
    val isReady: Boolean,
    val size: String?,
    val addedDate: Long = System.currentTimeMillis()
)