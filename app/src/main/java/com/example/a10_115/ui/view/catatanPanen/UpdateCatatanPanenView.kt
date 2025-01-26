package com.example.a10_115.ui.view.catatanPanen

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
import com.example.a10_115.ui.viewModel.catatanPanen.HomeCatatanPanenViewModel
import com.example.a10_115.ui.viewModel.catatanPanen.UpdateCatatanPanenViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateCatatanPanen : DestinasiNavigasi {
    override val route = "update_catatan"
    override val titleRes = "Update Data catatan"
    const val idPanen = "idPanen"
    val routeWithArgs = "$route/{$idPanen}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCatatanView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,// Navigasi kembali ke HomePekerja
    modifier: Modifier = Modifier,
    viewModel: UpdateCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelCatatanPanen: HomeCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    viewModelTanaman: HomeTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateCatatanPanen.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uicatatanPanenState,
            onCatatanValueChange = viewModel::updateInsertCatatanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateCatatanPanen() // Menyimpan data pekerja
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