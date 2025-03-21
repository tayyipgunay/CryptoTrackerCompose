package com.tayyipgunay.cryptotracker.presentation.cryptolist

sealed class CryptoEvent {
    data class GetCrypto(val order: String) : CryptoEvent()

}