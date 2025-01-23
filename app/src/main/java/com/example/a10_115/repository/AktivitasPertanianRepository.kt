package com.example.a10_115.repository

import com.example.a10_115.model.AktivitasPertanian
import com.example.a10_115.service.AktivitasPertanianService
import java.io.IOException

interface AktivitasPertanianRepository {
    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>
    suspend fun insertAktivitasPertanian(aktivitasPertanian: AktivitasPertanian)
    suspend fun updateAktivitasPertanian(idAktivitas: String, aktivitasPertanian: AktivitasPertanian)
    suspend fun deleteAktivitasPertanian(idAktivitas: String)
    suspend fun getAktivitasPertanianById(idAktivitas: String): AktivitasPertanian
}

class NetworkAktivitasPertanianRepository(
    private val aktivitasPertanianApiService: AktivitasPertanianService
) : AktivitasPertanianRepository {

    override suspend fun getAktivitasPertanian(): List<AktivitasPertanian> {
        return try {
            aktivitasPertanianApiService.getAktivitasPertanian()
        } catch (e: IOException) {
            throw IOException("Failed to fetch aktivitas pertanian. Network error occurred.", e)
        }
    }

    override suspend fun insertAktivitasPertanian(aktivitasPertanian: AktivitasPertanian) {
        val response = aktivitasPertanianApiService.insertAktivitasPertanian(aktivitasPertanian)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert aktivitas pertanian. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updateAktivitasPertanian(idAktivitas: String, aktivitasPertanian: AktivitasPertanian) {
        val response = aktivitasPertanianApiService.updateAktivitasPertanian(idAktivitas, aktivitasPertanian)
        if (!response.isSuccessful) {
            throw IOException("Failed to update aktivitas pertanian with ID: $idAktivitas. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deleteAktivitasPertanian(idAktivitas: String) {
        val response = aktivitasPertanianApiService.deleteAktivitasPertanian(idAktivitas)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete aktivitas pertanian with ID: $idAktivitas. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun getAktivitasPertanianById(idAktivitas: String): AktivitasPertanian {
        return try {
            val aktivitasPertanianList = aktivitasPertanianApiService.getAktivitasPertanianById(idAktivitas)
            if (aktivitasPertanianList.isNotEmpty()) {
                aktivitasPertanianList[0]
            } else {
                throw IOException("aktivitas pertanian with ID: $idAktivitas not found.")
            }
        } catch (e: IOException) {
            throw IOException("Failed to fetch catatan panen with ID: $idAktivitas. Network error occurred.", e)
        }
    }
}