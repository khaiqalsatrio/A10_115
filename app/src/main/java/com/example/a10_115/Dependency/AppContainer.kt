package com.example.a10_115.Dependency
import com.example.a10_115.repository.NetworkPekerjaRepository
import com.example.a10_115.repository.NetworkTanamanRepository
import com.example.a10_115.repository.PekerjaRepository
import com.example.a10_115.repository.TanamanRepository
import com.example.a10_115.service.PekerjaService
import com.example.a10_115.service.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pekerjaRepository: PekerjaRepository
    val tanamanRepository: TanamanRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2/API/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pekerjaService: PekerjaService by lazy { retrofit.create(PekerjaService::class.java) }
    override val pekerjaRepository: PekerjaRepository by lazy { NetworkPekerjaRepository(pekerjaService) }

    private val tanamanService: TanamanService by lazy { retrofit.create(TanamanService::class.java) }
    override val tanamanRepository: TanamanRepository by lazy { NetworkTanamanRepository(tanamanService) }
}
