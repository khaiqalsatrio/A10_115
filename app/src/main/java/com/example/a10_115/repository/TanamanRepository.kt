package com.example.a10_115.repository
import com.example.a10_115.model.Tanaman
import com.example.a10_115.service.TanamanService
import java.io.IOException

interface TanamanRepository {
    suspend fun getTanaman(): List<Tanaman>
    suspend fun insertTanaman(tanaman: Tanaman)
    suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman)
    suspend fun deleteTanaman(idTanaman: String)
    suspend fun getTanamanById(idTanaman: String): Tanaman
}

class NetworkTanamanRepository(
    private val tanamanApiService: TanamanService
) : TanamanRepository {

    override suspend fun getTanaman(): List<Tanaman> {
        return try {
            tanamanApiService.getTanaman()
        } catch (e: IOException) {
            throw IOException("Failed to fetch tanaman. Network error occurred.", e)
        }
    }

    override suspend fun insertTanaman(tanaman: Tanaman) {
        val response = tanamanApiService.insertTanaman(tanaman)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert tanaman. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman) {
        val response = tanamanApiService.updateTanaman(idTanaman, tanaman)
        if (!response.isSuccessful) {
            throw IOException("Failed to update tanaman with ID: $idTanaman. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deleteTanaman(idTanaman: String) {
        val response = tanamanApiService.deleteTanaman(idTanaman)
        if (!response.isSuccessful) {
            throw IOException("Failed to delete tanaman with ID: $idTanaman. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun getTanamanById(idTanaman: String): Tanaman {
        return try {
            val tanamanList = tanamanApiService.getTanamanById(idTanaman)
            if (tanamanList.isNotEmpty()) {
                tanamanList[0]
            } else {
                throw IOException("Tanaman with ID: $idTanaman not found.")
            }
        } catch (e: IOException) {
            throw IOException("Failed to fetch tanaman with ID: $idTanaman. Network error occurred.", e)
        }
    }
}