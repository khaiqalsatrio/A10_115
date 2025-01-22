package com.example.a10_115.ui.view.tanaman

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
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.tanaman.InsertUiEvent
import com.example.a10_115.ui.viewModel.tanaman.InsertUiState
import com.example.a10_115.ui.viewModel.tanaman.insertTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTanaman : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Masukan Data Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTanamanScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke Home tanaman
    modifier: Modifier = Modifier,
    viewModel: insertTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Tanaman") },
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
        EntryBodyTanaman(
            insertUiState = viewModel.uiState,
            onTanamanValueChange = viewModel::updateInsertTanamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTanaman() // Menyimpan data tanaman
                    navigateBack() // Navigasi kembali ke Home tanaman
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
fun EntryBodyTanaman(
    insertUiState: InsertUiState,
    onTanamanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTanaman(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onTanamanValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.idTanaman.isNotEmpty() && insertUiState.insertUiEvent.namaTanaman.isNotEmpty() // Validasi form
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
fun FormInputTanaman(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idTanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(idTanaman = it)) },
            label = { Text("id Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaTanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(namaTanaman = it)) },
            label = { Text("Nama Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.periodeTanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(periodeTanaman = it)) },
            label = { Text("Periode tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsiTanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsiTanaman = it)) },
            label = { Text("Deskripsi Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}