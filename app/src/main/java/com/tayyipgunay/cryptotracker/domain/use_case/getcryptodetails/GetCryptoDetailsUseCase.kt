package com.tayyipgunay.cryptotracker.domain.use_case.getcryptodetails

import coil.compose.AsyncImagePainter
import com.tayyipgunay.cryptotracker.data.remote.dto.toDetailCrypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail
import com.tayyipgunay.cryptotracker.domain.repository.CryptoRepository
import com.tayyipgunay.cryptotracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Error
import javax.inject.Inject


class GetCryptoDetailsUseCase @Inject constructor(private val cryptoRepository: CryptoRepository) {

    fun ExecuteGetCryptoDetails(id: String): Flow<Resource<List<CryptoDetail>>> = flow {
        cryptoRepository.getDetailCrypto(id).collect { ResourceDto ->
            when (ResourceDto) {
                is Resource.Success -> {
                    ResourceDto.data?.map { CryptoDto ->
                        val domainModel = CryptoDto.toDetailCrypto()
                        emit(Resource.Success(domainModel))


                    }
                }


                is Resource.Error -> {
                   emit(Resource.Error(ResourceDto.message!!))
                }

                is Resource.Loading -> {
                    emit(Resource.Loading())

                }
            }
        }
    }
}

