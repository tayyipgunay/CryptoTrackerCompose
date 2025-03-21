package com.tayyipgunay.cryptotracker.presentation.cryptolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tayyipgunay.cryptotracker.domain.use_case.getcryptolist.GetCryptoUseCase
import com.tayyipgunay.cryptotracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val getCryptoUseCase: GetCryptoUseCase) : ViewModel() {
    private var job: Job? = null
    private val _state = MutableStateFlow(CryptoState())
    val state: StateFlow<CryptoState> = _state.asStateFlow()


    init {
        //onEvent(CryptoEvent.GetCrypto("market_cap_desc"))

    }
    fun getCryptoList(order: String) {
        _state.update {
            it.copy(isLoading = true)
        }


        job = viewModelScope.launch {
            getCryptoUseCase.ExecuteGetCryptoList(order)
                .flowOn(Dispatchers.IO) // API çağrısı IO'da çalışır
                .collect { resource ->
                    withContext(Dispatchers.Main) { // 🔹 UI güncellemesi için Main Thread’e geçiyoruz
                        when (resource) {
                            is Resource.Success -> {
                               println(resource.data?.get(0) ?: "veri gelmediiijjj")
                                _state.update {
                                    it.copy(crypto = resource.data?: emptyList(), isLoading = false, error = "")

                                }

                            }

                            is Resource.Error -> {
                                // Hata gösterme işlemi
                                println(resource.message)
                                _state.update {
                                    it.copy(error = resource.message ?: "error", isLoading = false)
                                }
                            }

                            is Resource.Loading -> {
                                // Yüklenme göstergesi
                                _state.update {
                                    it.copy(isLoading = true)
                                }
                            }
                        }
                    }
                }
        }

    }
    fun onEvent(event: CryptoEvent){
        when(event){
            is CryptoEvent.GetCrypto -> {

             getCryptoList(event.order)
            }
        }
    }
}

