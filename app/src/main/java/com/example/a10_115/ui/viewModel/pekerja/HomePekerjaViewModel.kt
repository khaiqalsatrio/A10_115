package com.example.a10_115.ui.viewModel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.Pekerja
import com.example.a10_115.repository.PekerjaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val pekerja: List<Pekerja>): HomeUiState()
    object Error: HomeUiState()
    object Loading : HomeUiState()
}

class HomePekerjaViewModel(private val pekerja: PekerjaRepository): ViewModel() {

    var pekerjaUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPekerja()
    }

    fun getPekerja() {
        viewModelScope.launch {
            pekerjaUIState = HomeUiState.Loading
            pekerjaUIState = try {
                val pekerjaList = pekerja.getPekerja()  // Dapatkan data pekerja
                HomeUiState.Success(pekerjaList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deletePekerja(idPekerja: String) {
        println("Menghapus pekerja dengan ID: $idPekerja") // Debugging
        viewModelScope.launch {
            try {
                pekerja.deletePekerja(idPekerja) // Hapus pekerja
                pekerjaUIState = HomeUiState.Success(pekerja.getPekerja()) // Update UI
            } catch (e: IOException) {
                println("Error IO: ${e.message}")
                pekerjaUIState = HomeUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.message}")
                pekerjaUIState = HomeUiState.Error
            }
        }
    }
}