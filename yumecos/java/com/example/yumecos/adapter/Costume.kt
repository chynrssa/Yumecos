package com.example.yumecos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Costume(
    val id: Int,
    val name: String,
    val origin: String,
    val thumbnail: Int,
    val rating: Float,
    val ratingCount: Int,

    // Properti untuk halaman detail
    val priceRange: String, // Diubah dari 'price'
    val rentalPeriod: String, // Properti baru
    val description: String,
    val productImages: List<Int>,
    val sizeBadge: Int
) : Parcelable