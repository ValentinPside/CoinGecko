package com.example.coingecko.domain

data class Coin (
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String?,
    val price: Double?,
    val changePercent: Double?
)