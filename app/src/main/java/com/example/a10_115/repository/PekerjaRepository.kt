package com.example.a10_115.repository

import com.example.a10_115.model.Pekerja
import com.example.a10_115.service.PekerjaService
import java.io.IOException

interface PekerjaRepository {
    suspend fun getPekerja(): List<Pekerja>
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja)
    suspend fun deletePekerja(idPekerja: String)
    suspend fun getPekerjaById(idPekerja: String): Pekerja
}

class NetworkPekerjaRepository(
    private val pekerjaApiService: PekerjaService
) : PekerjaRepository {

    override suspend fun getPekerja(): List<Pekerja> {
        return try {
            pekerjaApiService.getPekerja()
        } catch (e: IOException) {
            throw IOException("Failed to fetch pekerja. Network error occurred.", e)
        }
    }

    override suspend fun insertPekerja(pekerja: Pekerja) {
        val response = pekerjaApiService.insertPekerja(pekerja)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert pekerja. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja) {
        val response = pekerjaApiService.updatePekerja(idPekerja, pekerja)
        if (!response.isSuccessful) {
            throw IOException("Failed to update pekerja with ID: $idPekerja. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deletePekerja(idPekerja: String) {
        val response = pekerjaApiService.deletePekerja(idPekerja)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete pekerja with ID: $idPekerja. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun getPekerjaById(idPekerja: String): Pekerja {
        return try {
            val pekerjaList = pekerjaApiService.getPekerjaById(idPekerja)
            if (pekerjaList.isNotEmpty()) {
                pekerjaList[0]
            } else {
                throw IOException("Pekerja with ID: $idPekerja not found.")
            }
        } catch (e: IOException) {
            throw IOException("Failed to fetch pekerja with ID: $idPekerja. Network error occurred.", e)
        }
    }
}