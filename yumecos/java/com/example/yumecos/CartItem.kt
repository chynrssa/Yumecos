// CartItem.kt
package com.example.yumecos

data class CartItem(
    val name: String,
    val description: String,
    val pricePerUnit: Double,
    var quantity: Int, // Gunakan 'var' karena kuantitas bisa berubah
    val size: String,
    val tag: String
) {
    fun getTotalPrice(): Double {
        return pricePerUnit * quantity
    }
}