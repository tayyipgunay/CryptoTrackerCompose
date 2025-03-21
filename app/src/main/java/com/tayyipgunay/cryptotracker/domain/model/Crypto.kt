package com.tayyipgunay.cryptotracker.domain.model

data class Crypto(
val id: String,
val name: String,
val symbol: String,
val currentPrice: Double,
val priceChangePercentage24h: Double,//yüzdesel değişim
    val image: String
)

