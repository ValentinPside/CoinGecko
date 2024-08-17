package com.example.coingecko.data.network.dto

data class InfoCoinDto(
    val categories: List<String>,
    val description: InfoDto,
    val image: ImageUrlDto
)