package com.example.a10_115.ui.viewModel.catatanPanen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.repository.CatatanPanenRepository
import com.example.a10_115.ui.view.catatanPanen.DestinasiDetailCatatanPanen
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailCatatanPanenUiState {
    data class Success(val catatanPanen: CatatanPanen) : DetailCatatanPanenUiState()
    object Error : DetailCatatanPanenUiState()
    object Loading : DetailCatatanPanenUiState()
}

class DetailCatatanPanenViewModel(
    savedStateHandle: SavedStateHandle,
    private val ctp: CatatanPanenRepository
) : ViewModel() {

    private val idPanen: String = checkNotNull(savedStateHandle[DestinasiDetailCatatanPanen.idPanen])
    var detailCatatanPanenUiState: DetailCatatanPanenUiState by mutableStateOf(DetailCatatanPanenUiState.Loading)
        private set

    init {
        getCatatanPanenById()
    }

    fun getCatatanPanenById() {
        viewModelScope.launch {
            detailCatatanPanenUiState = DetailCatatanPanenUiState.Loading
            detailCatatanPanenUiState = try {
                val catatanPanen = ctp.getCatatanPanenById(idPanen)
                DetailCatatanPanenUiState.Success(catatanPanen)
            } catch (e: IOException) {
                DetailCatatanPanenUiState.Error
            } catch (e: Exception) {
                DetailCatatanPanenUiState.Error
            }
        }
    }

    fun deleteCatatanPanen() {
        viewModelScope.launch {
            try {
                ctp.deleteCatatanPanen(idPanen)
                ctp.getCatatanPanen()
            } catch (e: IOException) {
                detailCatatanPanenUiState = DetailCatatanPanenUiState.Error
            } catch (e: HttpException) {
                detailCatatanPanenUiState = DetailCatatanPanenUiState.Error
            }
        }
    }
}