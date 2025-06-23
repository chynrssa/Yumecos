// Pesanan.kt
package com.example.yumecos

data class Pesanan(
    val id: String,
    val namaProduk: String,
    val namaGame: String,
    val tanggal: String,
    val harga: String,
    val status: String,
    val imageResId: Int
)