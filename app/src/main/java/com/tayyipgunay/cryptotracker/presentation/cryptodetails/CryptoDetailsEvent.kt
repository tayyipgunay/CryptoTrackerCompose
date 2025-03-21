package com.tayyipgunay.cryptotracker.presentation.cryptodetails

import com.tayyipgunay.cryptotracker.presentation.cryptolist.CryptoEvent

sealed class CryptoDetailsEvent {
    data class GetCryptoDetail(val id: String) : CryptoDetailsEvent()

}