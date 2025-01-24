package com.example.a10_115.ui.viewModel.aktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.model.AktivitasPertanian
import com.example.a10_115.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch

class insertAktivitasViewModel(private val aktivitasPertanian: AktivitasPertanianRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertAktivitasPertanianState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertAktivitasPertanian() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                aktivitasPertanian.insertAktivitasPertanian(uiState.insertUiEvent.toAktivitasPertanian())
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
    val idAktivitas: String = "",
    val idTanaman: String = "",
    val idPekerja: String = "",
    val tanggalAktivitas: String = "",
    val deskripsiAktivitas: String = "",
)

fun InsertUiEvent.toAktivitasPertanian(): AktivitasPertanian = AktivitasPertanian(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)

fun AktivitasPertanian.toUiStateAktivitasPertanian(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun AktivitasPertanian.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,  // Perbaikan di sini
    deskripsiAktivitas = deskripsiAktivitas
)