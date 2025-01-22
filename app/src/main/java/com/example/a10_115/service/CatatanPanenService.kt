package com.example.a10_115.service

import com.example.a10_115.model.CatatanPanen
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CatatanPanenService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("CatatanPanen/read.php")
    suspend fun getCatanPanen(): List<CatatanPanen>

    @GET("CatatanPanen/read1.php")
    suspend fun getCatatanPanenById(@Query("id_panen") idPanen: String): List<CatatanPanen>

    @POST("CatatanPanen/create.php")
    suspend fun insertCatatanPanen(@Body catatanPanen: CatatanPanen): Response<Void>

    @PUT("CatatanPanen/update.php")
    suspend fun updateCatatanPanen(
        @Query("id_panen") idPanen: String,
        @Body catatanPanen: CatatanPanen
    ): Response<Void>

    @DELETE("CatatanPanen/delete.php")
    suspend fun deleteCatatanPanen(@Query("id_panen") idPanen: String): Response<Void>
}