package com.example.a10_115.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatatanPanen(
    @SerialName("id_panen")
    val idPanen: String,

    @SerialName("id_tanaman")
    val idTanaman: String,

    @SerialName("tanggal_panen")
    val tanggalPanen: String,

    @SerialName("jumlah_panen")
    val jumlahPanen: String,

    val keterangan: String,
)
