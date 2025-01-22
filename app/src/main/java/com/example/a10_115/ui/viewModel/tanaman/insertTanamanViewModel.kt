package com.example.a10_115.ui.viewModel.tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10_115.model.Tanaman
import com.example.a10_115.repository.TanamanRepository
import kotlinx.coroutines.launch

class insertTanamanViewModel(private val tanaman: TanamanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertTanamanState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertTanaman() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                tanaman.insertTanaman(uiState.insertUiEvent.toTanaman())
                uiState = uiState.copy(isSuccess = true, isLoading = false) // Update state success
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false) // Handle error
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

data class InsertUiEvent(
    val idTanaman: String = "",
    val namaTanaman: String = "",
    val periodeTanaman: String = "",
    val deskripsiTanaman: String = "",
)

fun InsertUiEvent.toTanaman(): Tanaman = Tanaman(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanaman = periodeTanaman,
    deskripsiTanaman = deskripsiTanaman,
)

fun Tanaman.toUiStateTanaman(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Tanaman.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanaman = periodeTanaman,
    deskripsiTanaman = deskripsiTanaman  // Perbaikan di sini
)