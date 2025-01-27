package com.example.a10_115.ui.viewModel.aktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.repository.AktivitasPertanianRepository
import com.example.a10_115.ui.view.aktivitasPertanian.DestinasiUpdateAktivitasPertanian
import kotlinx.coroutines.launch

class UpdateAktivitasPertanianViewModel(
    savedStateHandle: SavedStateHandle,
    private val atv: AktivitasPertanianRepository
) : ViewModel() {

    var uiaktivitasPertanianState by mutableStateOf(InsertUiState())
        private set

    val idAktivitas: String = checkNotNull(savedStateHandle[DestinasiUpdateAktivitasPertanian.idAktivitas])

    init {
        viewModelScope.launch {
            uiaktivitasPertanianState = atv.getAktivitasPertanianById(idAktivitas).toUiStateAktivitasPertanian()
        }
    }

    fun updateInsertAktivitasPertanianState(insertUiEvent: InsertUiEvent) {
        uiaktivitasPertanianState = uiaktivitasPertanianState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAktivitasPertanian(){
        viewModelScope.launch {
            try {
                atv.updateAktivitasPertanian(idAktivitas, uiaktivitasPertanianState.insertUiEvent.toAktivitasPertanian())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}