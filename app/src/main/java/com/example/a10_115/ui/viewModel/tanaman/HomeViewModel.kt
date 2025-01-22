package com.example.a10_115.ui.viewModel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.Tanaman
import com.example.a10_115.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val tanaman: List<Tanaman>): HomeUiState()
    object Error: HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val tanaman: TanamanRepository): ViewModel() {

    var tanamanUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTanaman()
    }

    fun getTanaman() {
        viewModelScope.launch {
            tanamanUIState = HomeUiState.Loading
            tanamanUIState = try {
                val tanamanList = tanaman.getTanaman()  // Dapatkan data tanaman
                HomeUiState.Success(tanamanList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteTanaman(idTanaman: String) {
        println("Menghapus tanaman dengan ID: $idTanaman") // Debugging
        viewModelScope.launch {
            try {
                tanaman.deleteTanaman(idTanaman) // Hapus Tanaman
                tanamanUIState = HomeUiState.Success(tanaman.getTanaman()) // Update UI
            } catch (e: IOException) {
                println("Error IO: ${e.message}")
                tanamanUIState = HomeUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.message}")
                tanamanUIState = HomeUiState.Error
            }
        }
    }
}