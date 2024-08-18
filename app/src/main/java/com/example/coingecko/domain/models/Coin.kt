package com.example.coingecko.domain.models

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val price: String,
    val changePercent: String,
    val color: Int
)