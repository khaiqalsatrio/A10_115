package com.example.a10_115.ui.viewModel.aktivitasPertanian

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.AktivitasPertanian
import com.example.a10_115.repository.AktivitasPertanianRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val aktivitas: List<AktivitasPertanian>): HomeUiState()
    object Error: HomeUiState()
    object Loading : HomeUiState()
}

class HomeAktivitasViewModel(private val aktivitasPertanian: AktivitasPertanianRepository): ViewModel() {

    var aktivitasPertanianUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAktivitasPertanian()
    }

    fun getAktivitasPertanian() {
        viewModelScope.launch {
            aktivitasPertanianUIState = HomeUiState.Loading
            aktivitasPertanianUIState = try {
                val aktivitasPertanianList = aktivitasPertanian.getAktivitasPertanian()  // Dapatkan data catatan panen
                HomeUiState.Success(aktivitasPertanianList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteAktivitasPertanian(idAktivitas: String) {
        println("Menghapus aktivitas pertanian dengan ID: $idAktivitas") // Debugging
        viewModelScope.launch {
            try {
                aktivitasPertanian.deleteAktivitasPertanian(idAktivitas) // Hapus catatan panen
                aktivitasPertanianUIState = HomeUiState.Success(aktivitasPertanian.getAktivitasPertanian()) // Update UI
            } catch (e: IOException) {
                println("Error IO: ${e.message}")
                aktivitasPertanianUIState = HomeUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.message}")
                aktivitasPertanianUIState = HomeUiState.Error
            }
        }
    }
}