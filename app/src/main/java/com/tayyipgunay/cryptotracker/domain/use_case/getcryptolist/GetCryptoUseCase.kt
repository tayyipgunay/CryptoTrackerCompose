package com.tayyipgunay.cryptotracker.domain.use_case.getcryptolist

import com.tayyipgunay.cryptotracker.data.remote.dto.CryptoDto
import com.tayyipgunay.cryptotracker.data.remote.dto.toCrypto
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.repository.CryptoRepository
import com.tayyipgunay.cryptotracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.annotations.Async.Execute
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(private val repository: CryptoRepository) {

    /*fun ExecuteGetCryptoList(order: String): Flow<Resource<List<Crypto>>> = flow {
        repository.getCryptoList(order)
            .map { resourceList ->
                when (resourceList) {
                    is Resource.Success -> {
                        val cryptoDomainList =
                            resourceList.data?.map { it.toCrypto() } ?: "emptyList()"

                        Resource.Success(cryptoDomainList)
                    }

                    is Resource.Error -> {
                        Resource.Error(resourceList.message ?: "Unknown error")
                    }

                    is Resource.Loading -> {
                        Resource.Loading()
                    }
                }
            }
            .catch { e ->
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "Unexpected error"
                    )
                )
            } // Hata yönetimi
    }*/
    fun ExecuteGetCryptoList(order: String): Flow<Resource<List<Crypto>>> = flow {

        repository.getCryptoList(order).collect { ResourceList ->
            when (ResourceList) {
                is Resource.Success -> {
                    val cryptoList = ResourceList.data
                    cryptoList?.let { cryptoList ->
                        val cryptoDomainList = cryptoList.map { CryptoDto ->
                            CryptoDto.toCrypto()

                        }
                        emit(Resource.Success(cryptoDomainList))


                    } ?: emit(Resource.Error("data is null"))
                }

                is Resource.Error -> {
                    emit(Resource.Error(ResourceList.message!!))

                }

                is Resource.Loading -> {
                    emit(Resource.Loading())

                }
            }
        }
    }
}
