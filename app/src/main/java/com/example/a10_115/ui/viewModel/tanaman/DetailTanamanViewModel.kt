package com.example.a10_115.ui.viewModel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.Tanaman
import com.example.a10_115.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailTanamanUiState {
    data class Success(val tanaman: Tanaman) : DetailTanamanUiState()
    object Error : DetailTanamanUiState()
    object Loading : DetailTanamanUiState()
}

class DetailTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tnm: TanamanRepository
) : ViewModel() {

    private val idTanaman: String = checkNotNull(savedStateHandle[DestinasiDetailTanaman.idTanaman])
    var detailTanamanUiState: DetailTanamanUiState by mutableStateOf(DetailTanamanUiState.Loading)
        private set

    init {
        getTanamanById()
    }

    fun getTanamanById() {
        viewModelScope.launch {
            detailTanamanUiState = DetailTanamanUiState.Loading
            detailTanamanUiState = try {
                val tanaman = tnm.getTanamanById(idTanaman)
                DetailTanamanUiState.Success(tanaman)
            } catch (e: IOException) {
                DetailTanamanUiState.Error
            } catch (e: Exception) {
                DetailTanamanUiState.Error
            }
        }
    }

    fun deleteTanaman() {
        viewModelScope.launch {
            try {
                tnm.deleteTanaman(idTanaman)
                tnm.getTanaman()
            } catch (e: IOException) {
                detailTanamanUiState = DetailTanamanUiState.Error
            } catch (e: HttpException) {
                detailTanamanUiState = DetailTanamanUiState.Error
            }
        }
    }
}