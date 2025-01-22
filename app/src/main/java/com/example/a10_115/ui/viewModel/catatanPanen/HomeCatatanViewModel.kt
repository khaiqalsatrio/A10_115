package com.example.a10_115.ui.viewModel.catatanPanen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.repository.CatatanPanenRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val catatanPanen: List<CatatanPanen>): HomeUiState()
    object Error: HomeUiState()
    object Loading : HomeUiState()
}

class HomeCatatanViewModel(private val catatanPanen: CatatanPanenRepository): ViewModel() {

    var catatanPanenUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getCatatanPanen()
    }

    fun getCatatanPanen() {
        viewModelScope.launch {
            catatanPanenUIState = HomeUiState.Loading
            catatanPanenUIState = try {
                val catatanPanenList = catatanPanen.getCatatanPanen()  // Dapatkan data catatan panen
                HomeUiState.Success(catatanPanenList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteCatatanPanen(idPanen: String) {
        println("Menghapus catatan panen dengan ID: $idPanen") // Debugging
        viewModelScope.launch {
            try {
                catatanPanen.deleteCatatanPanen(idPanen) // Hapus catatan panen
                catatanPanenUIState = HomeUiState.Success(catatanPanen.getCatatanPanen()) // Update UI
            } catch (e: IOException) {
                println("Error IO: ${e.message}")
                catatanPanenUIState = HomeUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.message}")
                catatanPanenUIState = HomeUiState.Error
            }
        }
    }
}