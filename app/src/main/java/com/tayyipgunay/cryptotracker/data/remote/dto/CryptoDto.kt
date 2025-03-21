package com.tayyipgunay.cryptotracker.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail


data class CryptoDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("current_price")
    val currentPrice: Double,

    @SerializedName("market_cap")
    val marketCap: Double,  // Long → Double yaptık

    @SerializedName("market_cap_rank")
    val marketCapRank: Int?,  // Nullable yaptık

    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Double?, // Long → Double yaptık

    @SerializedName("total_volume")
    val totalVolume: Double,

    @SerializedName("high_24h")
    val high24h: Double,

    @SerializedName("low_24h")
    val low24h: Double,

    @SerializedName("price_change_24h")
    val priceChange24h: Double,

    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,

    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double,

    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,

    @SerializedName("circulating_supply")
    val circulatingSupply: Double,

    @SerializedName("total_supply")
    val totalSupply: Any?,  // Bazı coinlerde `Long`, bazılarında `Double` olabilir

    @SerializedName("max_supply")
    val maxSupply: Any?,  // Aynı şekilde farklı format olabilir

    @SerializedName("ath")
    val ath: Double,

    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double,

    @SerializedName("ath_date")
    val athDate: String?,  // Nullable yaptık

    @SerializedName("atl")
    val atl: Double,

    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double,

    @SerializedName("atl_date")
    val atlDate: String?,  // Nullable yaptık

    @SerializedName("last_updated")
    val lastUpdated: String?  // Nullable yaptık
)





fun CryptoDto.toCrypto(): Crypto {
    return Crypto(
        id = id,
        symbol = symbol,
        name = name,
        currentPrice = currentPrice,
        priceChangePercentage24h = priceChangePercentage24h,
        image = image

    )
}
fun CryptoDto.toDetailCrypto():List<CryptoDetail> {
    return listOf(
        CryptoDetail(
            id = id,
            name = name,
            symbol = symbol,
            image = image,
            currentPrice = currentPrice,
            priceChangePercentage24h = priceChangePercentage24h,
            low24h = low24h,
            high24h = high24h,
            marketCap = marketCap,
            totalVolume24h =totalVolume,
        )
    )
}
/*
 val id : String,
        val name : String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,  // Anlık fiyat
    val priceChange24h: Double,  // 24 saatlik fiyat değişimi
    val low24h: Double,  // 24 saat içindeki en düşük fiyat
    val high24h: Double,  // 24 saat içindeki en yüksek fiyat
    val marketCap: Double,  // Piyasa değeri
    val totalVolume24h: Double
 */
  

