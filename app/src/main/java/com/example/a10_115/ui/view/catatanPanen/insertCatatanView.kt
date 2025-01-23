package com.example.a10_115.ui.view.catatanPanen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.DynamicSelectedTextField
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.catatanPanen.InsertUiEvent
import com.example.a10_115.ui.viewModel.catatanPanen.InsertUiState
import com.example.a10_115.ui.viewModel.catatanPanen.insertCatatanPanenViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeTanamanViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeUiState
import kotlinx.coroutines.launch

object DestinasiEntryCatatan : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Masukan Data Catatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryCatatanScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: insertCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelTanaman: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Catatan") },
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
            onCatatanValueChange = viewModel::updateInsertCatatanPanenState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertCatatanPanen() // Menyimpan data pekerja
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
    onCatatanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputCatatan(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onCatatanValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelTanamanViewModel = viewModel() // Menambahkan viewModel Tanaman
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.idPanen.isNotEmpty() && insertUiState.insertUiEvent.idTanaman.isNotEmpty() // Validasi form
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
fun FormInputCatatan(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelTanamanViewModel: HomeTanamanViewModel // Menambahkan parameter untuk TanamanViewModel
) {
    // Ambil data tanaman dari HomeTanamanViewModel
    val tanamanUiState = viewModelTanamanViewModel.tanamanUiState

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
                    value = insertUiEvent.idPanen,
                    onValueChange = { onValueChange(insertUiEvent.copy(idPanen = it)) },
                    label = { Text("ID Panen") },
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
                    value = insertUiEvent.tanggalPanen,
                    onValueChange = { onValueChange(insertUiEvent.copy(tanggalPanen = it)) },
                    label = { Text("Tanggal Panen") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = insertUiEvent.jumlahPanen,
                    onValueChange = { onValueChange(insertUiEvent.copy(jumlahPanen = it)) },
                    label = { Text("Jumlah Panen") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = insertUiEvent.keterangan,
                    onValueChange = { onValueChange(insertUiEvent.copy(keterangan = it)) },
                    label = { Text("Keterangan") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
            }
        }
    }
}


