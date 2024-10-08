package com.example.coingecko.data.network.dto

import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price") val price: Double,
    @SerializedName("price_change_percentage_24h") val changePercent: Double
)
