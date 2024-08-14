package com.example.coingecko.data.network.dto

import com.google.gson.annotations.SerializedName

data class CoinDto(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String?,
    @SerializedName("current_price") val price: Double?,
    @SerializedName("ath_change_percentage") val changePercent: Double?
)
