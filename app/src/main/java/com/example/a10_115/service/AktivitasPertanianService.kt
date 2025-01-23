package com.example.a10_115.service

import com.example.a10_115.model.AktivitasPertanian
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AktivitasPertanianService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("AktivitasPertanian/read.php")
    suspend fun getAktivitasPertanian(): List<AktivitasPertanian>

    @GET("AktivitasPertanian/read1.php")
    suspend fun getAktivitasPertanianById(@Query("id_aktivitas") idAktivitas: String): List<AktivitasPertanian>

    @POST("AktivitasPertanian/create.php")
    suspend fun insertAktivitasPertanian(@Body aktivitasPertanian: AktivitasPertanian): Response<Void>

    @PUT("AktivitasPertanian/update.php")
    suspend fun updateAktivitasPertanian(
        @Query("id_aktivitas") idAktivitas: String,
        @Body aktivitasPertanian: AktivitasPertanian
    ): Response<Void>

    @DELETE("AktivitasPertanian/delete.php")
    suspend fun deleteAktivitasPertanian(@Query("id_aktivitas") idAktivitas: String): Response<Void>
}