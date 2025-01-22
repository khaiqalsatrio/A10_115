package com.example.a10_115.service

import com.example.a10_115.model.Pekerja
import com.example.a10_115.model.Tanaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TanamanService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("Tanaman/read.php")
    suspend fun getTanaman(): List<Tanaman>

    @GET("Tanaman/read1.php")
    suspend fun getTanamanById(@Query("id_tanaman") idTanaman: String): List<Tanaman>

    @POST("Tanaman/create.php")
    suspend fun insertTanaman(@Body tanaman: Tanaman): Response<Void>

    @PUT("Tanaman/update.php")
    suspend fun updateTanaman(
        @Query("id_tanaman") idTanaman: String,
        @Body tanaman: Tanaman
    ): Response<Void>

    @DELETE("Tanaman/delete.php")
    suspend fun deleteTanaman(@Query("id_tanaman") idTanaman: String): Response<Void>
}