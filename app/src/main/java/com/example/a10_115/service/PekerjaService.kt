package com.example.a10_115.service

import com.example.a10_115.model.Pekerja
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PekerjaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("Pekerja/read.php")
    suspend fun getPekerja(): List<Pekerja>

    @GET("Pekerja/read1.php")
    suspend fun getPekerjaById(@Query("id_pekerja") idPekerja: String): List<Pekerja>

    @POST("Pekerja/create.php")
    suspend fun insertPekerja(@Body pekerja: Pekerja): Response<Void>

    @PUT("Pekerja/update.php")
    suspend fun updatePekerja(
        @Query("id_pekerja") idPekerja: String,
        @Body pekerja: Pekerja
    ): Response<Void>

    @DELETE("Pekerja/delete.php")
    suspend fun deletePekerja(@Query("id_pekerja") idPekerja: String): Response<Void>
}