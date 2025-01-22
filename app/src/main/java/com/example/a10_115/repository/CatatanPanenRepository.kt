package com.example.a10_115.repository

import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.service.CatatanPanenService
import java.io.IOException

interface CatatanPanenRepository {
    suspend fun getCatatanPanen(): List<CatatanPanen>
    suspend fun insertCatatanPanen(catatanPanen: CatatanPanen)
    suspend fun updateCatatanPanen(idPanen: String, catatanPanen: CatatanPanen)
    suspend fun deleteCatatanPanen(idTanaman: String)
    suspend fun getCatatanPanenById(tanggalPanen: String): CatatanPanen
}

class NetworkCatatanPanenRepository(
    private val catatanApiService: CatatanPanenService
) : CatatanPanenRepository {

    override suspend fun getCatatanPanen(): List<CatatanPanen> {
        return try {
            catatanApiService.getCatanPanen()
        } catch (e: IOException) {
            throw IOException("Failed to fetch catatan panen. Network error occurred.", e)
        }
    }

    override suspend fun insertCatatanPanen(catatanPanen: CatatanPanen) {
        val response = catatanApiService.insertCatatanPanen(catatanPanen)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert catatan panen. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updateCatatanPanen(idPanen: String, catatanPanen: CatatanPanen) {
        val response = catatanApiService.updateCatatanPanen(idPanen, catatanPanen)
        if (!response.isSuccessful) {
            throw IOException("Failed to update catatan panen with ID: $idPanen. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deleteCatatanPanen(idPanen: String) {
        val response = catatanApiService.deleteCatatanPanen(idPanen)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete pekerja with ID: $idPanen. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun getCatatanPanenById(idPanen: String): CatatanPanen {
        return try {
            val catatanPanenList = catatanApiService.getCatatanPanenById(idPanen)
            if (catatanPanenList.isNotEmpty()) {
                catatanPanenList[0]
            } else {
                throw IOException("catatan panen with ID: $idPanen not found.")
            }
        } catch (e: IOException) {
            throw IOException("Failed to fetch catatan panen with ID: $idPanen. Network error occurred.", e)
        }
    }
}

