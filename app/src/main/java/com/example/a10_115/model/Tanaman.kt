package com.example.a10_115.model

import kotlinx.serialization.SerialName

data class Tanaman(
    @SerialName("id_tanaman")
    val idTanaman: String,

    @SerialName("nama_tanaman")
    val namaTanaman: String,

    @SerialName("periode_tanaman")
    val periodeTanaman: String,

    @SerialName("deskripsi_tanaman")
    val deskripsiTanaman: String,
)
