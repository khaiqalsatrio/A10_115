package com.example.a10_115.repository

import com.example.a10_115.service.PekerjaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pekerjaRepository: PekerjaRepository
}

class PekerjaContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2/API/" // Jika menggunakan emulator
    private val json = Json { ignoreUnknownKeys = true } // Menangani field yang tidak ada di model
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Konversi JSON ke model
        .baseUrl(baseUrl)
        .build()

    // Inisialisasi service
    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java)
    }

    // Inisialisasi repository
    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkPekerjaRepository(pekerjaService)
    }
}