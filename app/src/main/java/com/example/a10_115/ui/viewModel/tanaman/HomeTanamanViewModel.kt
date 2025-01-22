// HomeTanamanViewModel.kt

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

class HomeTanamanViewModel(private val tanamanRepository: TanamanRepository) : ViewModel() {

    var tanamanUiState by mutableStateOf<HomeUiState>(HomeUiState.Loading)
        private set

    init {
        getTanaman()
    }

    fun getTanaman() {
        viewModelScope.launch {
            tanamanUiState = HomeUiState.Loading
            try {
                val tanamanList = tanamanRepository.getTanaman()
                if (tanamanList.isEmpty()) {
                    tanamanUiState = HomeUiState.Error // Atau beri state khusus jika data kosong
                } else {
                    tanamanUiState = HomeUiState.Success(tanamanList)
                }
            } catch (e: IOException) {
                tanamanUiState = HomeUiState.Error
            } catch (e: HttpException) {
                tanamanUiState = HomeUiState.Error
            }
        }
    }

    fun deleteTanaman(idTanaman: String) {
        viewModelScope.launch {
            try {
                tanamanRepository.deleteTanaman(idTanaman)
                getTanaman() // Refresh data tanaman setelah dihapus
            } catch (e: IOException) {
                tanamanUiState = HomeUiState.Error
            } catch (e: HttpException) {
                tanamanUiState = HomeUiState.Error
            }
        }
    }
}

