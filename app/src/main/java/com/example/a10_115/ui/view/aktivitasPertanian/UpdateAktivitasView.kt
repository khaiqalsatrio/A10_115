package com.example.a10_115.ui.view.aktivitasPertanian

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.CostumeTopAppBar
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.aktivitasPertanian.UpdateAktivitasPertanianViewModel
import com.example.a10_115.ui.viewModel.pekerja.HomePekerjaViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateAktivitasPertanian : DestinasiNavigasi {
    override val route = "update_aktivitas"
    override val titleRes = "Update Data aktivitas"
    const val idAktivitas = "idAktivitas"
    val routeWithArgs = "$route/{$idAktivitas}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAktivitasView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasPertanianViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelPekerja: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelTanaman: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateAktivitasPertanian.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiaktivitasPertanianState,
            onAktivitasPertanianValueChange = viewModel::updateInsertAktivitasPertanianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAktivitasPertanian() // Menyimpan data pekerja
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