package com.example.finalminiproject

import java.io.Serializable

data class Property(
    val id: String,
    val name: String,
    val type: String,
    val price: Long,
    val address: String,
    val location: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val areaSqFt: Int,
    val imageUrls: List<String>
) : Serializable