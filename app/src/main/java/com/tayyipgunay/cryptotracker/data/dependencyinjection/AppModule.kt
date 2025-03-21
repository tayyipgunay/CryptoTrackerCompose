package com.tayyipgunay.cryptotracker.data.dependencyinjection

import com.tayyipgunay.cryptotracker.data.remote.CryptoAPI
import com.tayyipgunay.cryptotracker.data.repository.CryptoRepositoryIMPL
import com.tayyipgunay.cryptotracker.domain.repository.CryptoRepository
import com.tayyipgunay.cryptotracker.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoAPI(): CryptoAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)

    }
    @Provides
    @Singleton
    fun provideCryptoRepository(api: CryptoAPI): CryptoRepository {
        return CryptoRepositoryIMPL(api)
    }

}


