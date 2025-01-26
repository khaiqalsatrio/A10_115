package com.example.a10_115.ui.viewModel.aktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.AktivitasPertanian
import com.example.a10_115.repository.AktivitasPertanianRepository
import com.example.a10_115.ui.view.aktivitasPertanian.DestinasiDetailAktivitasPertanian
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailAktivitasUiState {
    data class Success(val aktivitasPertanian: AktivitasPertanian) : DetailAktivitasUiState()
    object Error : DetailAktivitasUiState()
    object Loading : DetailAktivitasUiState()
}

class DetailAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val atv: AktivitasPertanianRepository
) : ViewModel() {

    private val idAktivitas: String = checkNotNull(savedStateHandle[DestinasiDetailAktivitasPertanian.idAktivitas])
    var detailAktivitasUiState: DetailAktivitasUiState by mutableStateOf(DetailAktivitasUiState.Loading)
        private set

    init {
        getAktivitasPertanianById()
    }

    fun getAktivitasPertanianById() {
        viewModelScope.launch {
            detailAktivitasUiState = DetailAktivitasUiState.Loading
            detailAktivitasUiState = try {
                val aktivitasPertanian = atv.getAktivitasPertanianById(idAktivitas)
                DetailAktivitasUiState.Success(aktivitasPertanian)
            } catch (e: IOException) {
                DetailAktivitasUiState.Error
            } catch (e: Exception) {
                DetailAktivitasUiState.Error
            }
        }
    }

    fun deleteAktivitas() {
        viewModelScope.launch {
            try {
                atv.deleteAktivitasPertanian(idAktivitas)
                atv.getAktivitasPertanian()
            } catch (e: IOException) {
                detailAktivitasUiState = DetailAktivitasUiState.Error
            } catch (e: HttpException) {
                detailAktivitasUiState = DetailAktivitasUiState.Error
            }
        }
    }
}