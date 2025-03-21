package com.tayyipgunay.cryptotracker.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.tayyipgunay.cryptotracker.data.remote.CryptoAPI
import com.tayyipgunay.cryptotracker.data.remote.dto.CryptoDto
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail
import com.tayyipgunay.cryptotracker.domain.repository.CryptoRepository
import com.tayyipgunay.cryptotracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CryptoRepositoryIMPL @Inject constructor(private val api: CryptoAPI): CryptoRepository  {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCryptoList(order: String): Flow<Resource<List<CryptoDto>>> =flow {
        try {
            emit(Resource.Loading())
            val response = api.getCryptoList(order = order)
            if (response.isSuccessful) {
                response.body()?.let {body->
                    if (body.isNotEmpty()) {
                         emit(Resource.Success(body))
                    }
                    else {
                        emit(Resource.Error(" data is empty"))

                    }
                }?: emit(Resource.Error("data is null"))
            }
            else{
                emit(Resource.Error(response.code().toString()))
            }
        }

        catch (e: IOException) {
            // 🔹 İnternet bağlantısı yoksa özel hata mesajı
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: HttpException) {
            // 🔹 API hatalarını yakalamak için
            emit(Resource.Error("Server error: ${e.message}"))
        } catch (e: Exception) {
            // 🔹 Beklenmeyen hataları yakalamak için
            emit(Resource.Error("Unexpected Error: ${e.message}"))
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDetailCrypto(id: String): Flow<Resource<List<CryptoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val response=api.getCryptoDetails(id = id)
            if(response.isSuccessful){
                response.body()?.let { body->

                  emit(Resource.Success(body))

                }?:emit(Resource.Error(message = "response is empty"))

                }


        }
        catch (e: IOException) {
            // 🔹 İnternet bağlantısı yoksa özel hata mesajı
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: HttpException) {
            // 🔹 API hatalarını yakalamak için
            emit(Resource.Error("Server error: ${e.message}"))
        } catch (e: Exception) {
            // 🔹 Beklenmeyen hataları yakalamak için
            emit(Resource.Error("Unexpected Error: ${e.message}"))
        }


    }


}