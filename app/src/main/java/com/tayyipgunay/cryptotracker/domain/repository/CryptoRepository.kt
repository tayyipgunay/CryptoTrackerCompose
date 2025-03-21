package com.tayyipgunay.cryptotracker.domain.repository

import com.tayyipgunay.cryptotracker.data.remote.dto.CryptoDto
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail
import com.tayyipgunay.cryptotracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    suspend fun getCryptoList(order: String): Flow<Resource<List<CryptoDto>>>

    suspend fun getDetailCrypto(id:String):   Flow<Resource<List<CryptoDto>>>





}