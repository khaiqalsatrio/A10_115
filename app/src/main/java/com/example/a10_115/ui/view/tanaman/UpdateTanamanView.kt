package com.example.a10_115.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.CostumeTopAppBar
import com.example.a10_115.ui.view.pekerja.EntryBody
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.pekerja.UpdatePekerjaViewModel
import com.example.a10_115.ui.viewModel.tanaman.UpdateTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTanaman : DestinasiNavigasi {
    override val route = "update_tanaman"
    override val titleRes = "Update Data tanaman"
    const val idTanaman = "idTanaman"
    val routeWithArgs = "$route/{$idTanaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTanamanView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTanaman.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBodyTanaman(
            insertTanamanUiState = viewModel.uitanamanState,
            onTanamanValueChange =  viewModel::updateInsertTanamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTanaman() // Menyimpan data pekerja
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