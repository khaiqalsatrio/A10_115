package com.example.a10_115.ui.view.pekerja

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
import com.example.a10_115.ui.viewModel.pekerja.InsertUiEvent
import com.example.a10_115.ui.viewModel.pekerja.InsertUiState
import com.example.a10_115.ui.viewModel.pekerja.insertPekerjaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPekerja : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Masukan Data Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPekerjaScreen(
    navigateBack: () -> Unit, // Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: insertPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = { Text("Masukan Data Pekerja") },
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
            onSiswaValueChange = viewModel::updateInsertPekerjaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPekerja() // Menyimpan data pekerja
                    navigateBack() // Navigasi kembali ke HomePekerja
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
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPekerja(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.insertUiEvent.idPekerja.isNotEmpty() && insertUiState.insertUiEvent.namaPekerja.isNotEmpty() // Validasi form
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
fun FormInputPekerja(
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
            value = insertUiEvent.idPekerja,
            onValueChange = { onValueChange(insertUiEvent.copy(idPekerja = it)) },
            label = { Text("id Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namaPekerja,
            onValueChange = { onValueChange(insertUiEvent.copy(namaPekerja = it)) },
            label = { Text("Nama Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.jabatan,
            onValueChange = { onValueChange(insertUiEvent.copy(jabatan = it)) },
            label = { Text("Jabatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.kontakPekerja,
            onValueChange = { onValueChange(insertUiEvent.copy(kontakPekerja = it)) },
            label = { Text("Kontak Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}