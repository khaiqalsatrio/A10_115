package com.example.a10_115.ui.viewModel.pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.model.Pekerja
import com.example.a10_115.repository.CatatanPanenRepository
import com.example.a10_115.repository.PekerjaRepository
import com.example.a10_115.ui.view.catatanPanen.DestinasiDetailCatatanPanen
import com.example.a10_115.ui.view.pekerja.DestinasiDetailPekerja
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPekerjaUiState {
    data class Success(val pekerja: Pekerja) : DetailPekerjaUiState()
    object Error : DetailPekerjaUiState()
    object Loading : DetailPekerjaUiState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pkj: PekerjaRepository
) : ViewModel() {

    private val idPekerja: String = checkNotNull(savedStateHandle[DestinasiDetailPekerja.idPekerja])
    var detailPekerjaUiState: DetailPekerjaUiState by mutableStateOf(DetailPekerjaUiState.Loading)
        private set

    init {
        getPekerjaById()
    }

    fun getPekerjaById() {
        viewModelScope.launch {
            detailPekerjaUiState = DetailPekerjaUiState.Loading
            detailPekerjaUiState = try {
                val pekerja = pkj.getPekerjaById(idPekerja)
                DetailPekerjaUiState.Success(pekerja)
            } catch (e: IOException) {
                DetailPekerjaUiState.Error
            } catch (e: Exception) {
                DetailPekerjaUiState.Error
            }
        }
    }

    fun deletePekerja() {
        viewModelScope.launch {
            try {
                pkj.deletePekerja(idPekerja)
                pkj.getPekerja()
            } catch (e: IOException) {
                detailPekerjaUiState = DetailPekerjaUiState.Error
            } catch (e: HttpException) {
                detailPekerjaUiState = DetailPekerjaUiState.Error
            }
        }
    }
}