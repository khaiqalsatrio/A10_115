package com.example.a10_115.ui.view.aktivitasPertanian

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.DynamicSelectedTextField
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.aktivitasPertanian.InsertUiEvent
import com.example.a10_115.ui.viewModel.aktivitasPertanian.InsertUiState
import com.example.a10_115.ui.viewModel.aktivitasPertanian.insertAktivitasViewModel
import com.example.a10_115.ui.viewModel.pekerja.HomePekerjaViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeTanamanViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeUiState
import kotlinx.coroutines.launch

object DestinasiEntryAktivitas : DestinasiNavigasi {
    override val route = "item_entry_aktivitas"
    override val titleRes = "Masukan Data Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAktivitasPertanianScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: insertAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelTanaman: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelPekerja: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Aktivitas") },
                navigationIcon = { // Tambahkan ikon panah untuk kembali
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Ikon panah kiri
                            contentDescription = "Kembali ke Home"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onAktivitasPertanianValueChange = viewModel::updateInsertAktivitasPertanianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAktivitasPertanian() // Menyimpan data pekerja
                    navigateBack()  // Navigasi kembali ke HomePekerja
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onAktivitasPertanianValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputAktivitas(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAktivitasPertanianValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelTanamanViewModel = viewModel(), // Menambahkan viewModel Tanaman
            viewModelPekerjaViewModel = viewModel()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.idAktivitas.isNotEmpty() && insertUiState.insertUiEvent.idTanaman.isNotEmpty() // Validasi form
        ) {
            if (insertUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp)) // Loading indicator
            } else {
                Text(text = "Simpan")
            }
        }
    }
}

@Composable
fun FormInputAktivitas(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelTanamanViewModel: HomeTanamanViewModel, // Menambahkan parameter untuk TanamanViewModel
    viewModelPekerjaViewModel: HomePekerjaViewModel
) {
    // Ambil data tanaman dari HomeTanamanViewModel
    val tanamanUiState = viewModelTanamanViewModel.tanamanUiState
    val pekerjaUiState = viewModelPekerjaViewModel.pekerjaUIState

    // Menampilkan UI berdasarkan state
    when (tanamanUiState) {
        is HomeUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is HomeUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
        }
        is HomeUiState.Success -> {
            val tanamanList = tanamanUiState.tanaman // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = insertUiEvent.idAktivitas,
                    onValueChange = { onValueChange(insertUiEvent.copy(idAktivitas = it)) },
                    label = { Text("ID Aktivitas") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                DynamicSelectedTextField(
                    selectedValue = insertUiEvent.idTanaman.toString(), // Mengonversi idTanaman ke String
                    options = tanamanList.map { it.idTanaman.toString() }, // Mengonversi ID tanaman ke String
                    label = "Pilih ID Tanaman",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertUiEvent.copy(idTanaman = selectedId))
                    }
                )
                OutlinedTextField(
                    value = insertUiEvent.tanggalAktivitas,
                    onValueChange = { onValueChange(insertUiEvent.copy(tanggalAktivitas = it)) },
                    label = { Text("Tanggal Aktivitas") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = insertUiEvent.deskripsiAktivitas,
                    onValueChange = { onValueChange(insertUiEvent.copy(deskripsiAktivitas = it)) },
                    label = { Text("Deskripsi Aktivitas") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
            }
        }
    }
    when (pekerjaUiState) {
        is com.example.a10_115.ui.viewModel.pekerja.HomeUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is com.example.a10_115.ui.viewModel.pekerja.HomeUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data pekerja", color = MaterialTheme.colorScheme.error)
        }
        is com.example.a10_115.ui.viewModel.pekerja.HomeUiState.Success -> {
            val pekerjaList = pekerjaUiState.pekerja // Mendapatkan list tanaman
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DynamicSelectedTextField(
                    selectedValue = insertUiEvent.idPekerja.toString(), // Mengonversi idTanaman ke String
                    options = pekerjaList.map { it.idPekerja.toString() }, // Mengonversi ID tanaman ke String
                    label = "Pilih ID Pekerja",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(insertUiEvent.copy(idPekerja = selectedId))
                    }
                )
            }
        }
    }
}