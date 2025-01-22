package com.example.a10_115.ui.viewModel.catatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.model.Pekerja
import com.example.a10_115.repository.CatatanPanenRepository
import kotlinx.coroutines.launch

class insertCatatanPanenViewModel(private val catatanPanen: CatatanPanenRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertCatatanPanenState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertCatatanPanen() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                catatanPanen.insertCatatanPanen(uiState.insertUiEvent.toCatatanPanen())
                uiState = uiState.copy(isSuccess = true, isLoading = false) // Update state success
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false) // Handle error
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertUiEvent(
    val idPanen: String = "",
    val idTanaman: String = "",
    val tanggalPanen: String = "",
    val jumlahPanen: String = "",
    val keterangan: String = "",
)

fun InsertUiEvent.toCatatanPanen(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUiStateCatatanPanen(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun CatatanPanen.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggalPanen,
    jumlahPanen = jumlahPanen,  // Perbaikan di sini
    keterangan = keterangan
)