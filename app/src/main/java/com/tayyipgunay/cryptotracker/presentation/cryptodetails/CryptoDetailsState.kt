package com.tayyipgunay.cryptotracker.presentation.cryptodetails

import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail

data class CryptoDetailsState(
    val isLoading: Boolean = false,
    val cryptoDetail: List<CryptoDetail> = emptyList(),
val error: String = ""
)