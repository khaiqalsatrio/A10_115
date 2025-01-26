package com.example.a10_115.ui.viewModel.catatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.repository.CatatanPanenRepository
import com.example.a10_115.ui.view.catatanPanen.DestinasiUpdateCatatanPanen
import kotlinx.coroutines.launch

class UpdateCatatanPanenViewModel(
    savedStateHandle: SavedStateHandle,
    private val ctp: CatatanPanenRepository
) : ViewModel() {

    var uicatatanPanenState by mutableStateOf(InsertUiState())
        private set

    val idPanen: String = checkNotNull(savedStateHandle[DestinasiUpdateCatatanPanen.idPanen])

    init {
        viewModelScope.launch {
            uicatatanPanenState = ctp.getCatatanPanenById(idPanen).toUiStateCatatanPanen()
        }
    }

    fun updateInsertCatatanState(insertUiEvent: InsertUiEvent) {
        uicatatanPanenState = uicatatanPanenState.copy(insertUiEvent = insertUiEvent)
    }


    suspend fun updateCatatanPanen(){
        viewModelScope.launch {
            try {
                ctp.updateCatatanPanen(idPanen, uicatatanPanenState.insertUiEvent.toCatatanPanen())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}