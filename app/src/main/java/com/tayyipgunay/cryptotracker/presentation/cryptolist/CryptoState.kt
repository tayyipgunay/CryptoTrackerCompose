package com.tayyipgunay.cryptotracker.presentation.cryptolist

import com.tayyipgunay.cryptotracker.domain.model.Crypto

data class CryptoState(
    val isLoading: Boolean = false,
    val crypto: List<Crypto> = emptyList(),
    val error: String = ""
)