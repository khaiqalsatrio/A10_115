package com.example.a10_115.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AktivitasPertanian(
    @SerialName("id_Aktifitas")
    val idAktifitas: String,

    @SerialName("id_tanaman")
    val idTanaman: String,

    @SerialName("id_pekerja")
    val idPekerja: String,

    @SerialName("tanggal_aktifitas")
    val tanggalPanen: String,

    @SerialName("deskripsi_aktifitas")
    val deskripsiAktifitas: String,
)
