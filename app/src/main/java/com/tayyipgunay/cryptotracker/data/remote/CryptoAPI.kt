package com.tayyipgunay.cryptotracker.data.remote

import com.tayyipgunay.cryptotracker.data.remote.dto.CryptoDto
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail
import com.tayyipgunay.cryptotracker.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {

    @GET(Constants.EXCHANGE_RATE_ENDPOINT)
    suspend fun getCryptoList(
        @Query("vs_currency") vsCurrency: String = "usd",
       // @Query("per_page") perPage: Int = 100,
        //@Query("page") page: Int = 1,
        @Query("order") order: String,
        @Query("x_cg_demo_api_key") apikey: String = Constants.API_KEY,
    ): Response<List<CryptoDto>>

    @GET(Constants.EXCHANGE_RATE_ENDPOINT)
    suspend fun getCryptoDetails(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") id:String,
        @Query("x_cg_demo_api_key") apikey: String = Constants.API_KEY
        ):Response<List<CryptoDto>>
}