package com.example.a10_115.ui.viewModel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.repository.CatatanPanenRepository
import com.example.a10_115.repository.PekerjaRepository
import com.example.a10_115.ui.view.pekerja.DestinasiUpdatePekerja
import com.example.a10_115.ui.viewModel.catatanPanen.InsertUiEvent
import kotlinx.coroutines.launch

class UpdatePekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pkj: PekerjaRepository
) : ViewModel() {

    var uipekerjaState by mutableStateOf(InsertPekerjaUiState())
        private set

    val idPekerja: String = checkNotNull(savedStateHandle[DestinasiUpdatePekerja.idPekerja])

    init {
        viewModelScope.launch {
            uipekerjaState = pkj.getPekerjaById(idPekerja).toUiStatePekerja()
        }
    }

    fun updateInsertPekerjaState(insertPekerjaUiEvent: InsertPekerjaUiEvent) {
        uipekerjaState = uipekerjaState.copy(insertPekerjaUiEvent = insertPekerjaUiEvent)
    }


    suspend fun updatePekerja(){
        viewModelScope.launch {
            try {
                pkj.updatePekerja(idPekerja, uipekerjaState.insertPekerjaUiEvent.toPekerja())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}