package com.tayyipgunay.cryptotracker.domain.model

data class CryptoDetail(
val id: String,
val name: String,
val symbol: String,
val image: String,
val currentPrice: Double,  // Anlık fiyat
val priceChangePercentage24h: Double,//yüzdesel değişim
val low24h: Double,  // 24 saat içindeki en düşük fiyat
val high24h: Double,  // 24 saat içindeki en yüksek fiyat
val marketCap: Double,  // Piyasa değeri
val totalVolume24h: Double  // 24 saatlik işlem hacmi
)

