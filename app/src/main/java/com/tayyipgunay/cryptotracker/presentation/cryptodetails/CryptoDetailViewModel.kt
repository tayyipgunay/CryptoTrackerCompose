package com.tayyipgunay.cryptotracker.presentation.cryptodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tayyipgunay.cryptotracker.domain.use_case.getcryptodetails.GetCryptoDetailsUseCase
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
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor
    (private val cryptoDetailsUseCase: GetCryptoDetailsUseCase) :ViewModel() {
    private var job: Job? = null


    private val _state = MutableStateFlow(CryptoDetailsState())
    val state: StateFlow<CryptoDetailsState> = _state.asStateFlow()


    fun getCryptoDetails(id: String) {
        job?.cancel()

        _state.update {
            it.copy(isLoading = true)
        }
        job = viewModelScope.launch {
            cryptoDetailsUseCase.ExecuteGetCryptoDetails(id)
                .flowOn(Dispatchers.IO).collect { resource ->

                    _state.update { currentState ->
                        when (resource) {

                            is Resource.Success ->
                                currentState.copy(
                                    isLoading = false,
                                    error = "",
                                    cryptoDetail = resource.data!!
                                )

                            is Resource.Error ->
                                currentState.copy(
                                    isLoading = false, error = resource.message ?: "error",
                                    cryptoDetail = emptyList()
                                )


                            is Resource.Loading ->
                                currentState.copy(isLoading = true)


                        }

                    }
                }


        }

    }

    fun onEvent(event: CryptoDetailsEvent) {
        when (event) {
            is CryptoDetailsEvent.GetCryptoDetail -> {
                getCryptoDetails(event.id)
            }
        }
    }
}



