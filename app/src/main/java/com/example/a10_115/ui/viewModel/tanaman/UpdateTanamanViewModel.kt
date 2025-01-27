package com.example.a10_115.ui.viewModel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.repository.TanamanRepository
import com.example.a10_115.ui.view.tanaman.DestinasiUpdateTanaman
import kotlinx.coroutines.launch

class UpdateTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tnm: TanamanRepository
) : ViewModel() {

    var uitanamanState by mutableStateOf(InsertTanamanUiState())
        private set

    val idTanaman: String = checkNotNull(savedStateHandle[DestinasiUpdateTanaman.idTanaman])

    init {
        viewModelScope.launch {
            uitanamanState = tnm.getTanamanById(idTanaman).toUiStateTanaman()
        }
    }

    fun updateInsertTanamanState(insertTanamanaUiEvent: InsertTanamanUiEvent) {
        uitanamanState = uitanamanState.copy(insertTanamanUiEvent = insertTanamanaUiEvent)
    }

    suspend fun updateTanaman(){
        viewModelScope.launch {
            try {
                tnm.updateTanaman(idTanaman, uitanamanState.insertTanamanUiEvent.toTanaman())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}