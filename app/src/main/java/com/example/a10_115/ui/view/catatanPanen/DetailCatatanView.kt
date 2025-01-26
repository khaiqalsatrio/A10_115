package com.example.a10_115.ui.view.catatanPanen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.CostumeTopAppBar
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.catatanPanen.DetailCatatanPanenUiState
import com.example.a10_115.ui.viewModel.catatanPanen.DetailCatatanPanenViewModel

object DestinasiDetailCatatanPanen : DestinasiNavigasi {
    override val route = "detail_catatan"
    override val titleRes = "Detail Catatan"
    const val idPanen = "idPanen"
    val routeWithArgs = "$route/{$idPanen}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCatatanPanenView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailCatatanPanenViewModel: DetailCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val detailUiState = detailCatatanPanenViewModel.detailCatatanPanenUiState

    LaunchedEffect (Unit){
        detailCatatanPanenViewModel.getCatatanPanenById()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailCatatanPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val idPanen = (detailUiState as? DetailCatatanPanenUiState.Success)?.catatanPanen?.idPanen
                    if (idPanen != null) {
                        onEditClick(idPanen)
                    } else {
                        println("Error: ID Panen not available")
                    }
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Catatan Panen"
                )
            }
        }
    ) { innerPadding ->
        DetailCatatanPanenContent(
            dtlCatatanPanenUiState = detailUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDeleteClick = {
                detailCatatanPanenViewModel.deleteCatatanPanen()
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailCatatanPanenContent(
    dtlCatatanPanenUiState: DetailCatatanPanenUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (dtlCatatanPanenUiState) {
        is DetailCatatanPanenUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailCatatanPanenUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is DetailCatatanPanenUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailCatatanPanenCard(catatanPanen = dtlCatatanPanenUiState.catatanPanen)
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hapus Acara")
                }
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
fun DetailCatatanPanenCard(
    catatanPanen: CatatanPanen,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailCatatanPanen(judul = "ID Panen", isinya = catatanPanen.idPanen ?: "Tidak tersedia")
            ComponentDetailCatatanPanen(judul = "ID Tanaman", isinya = catatanPanen.idTanaman ?: "Tidak tersedia")
            ComponentDetailCatatanPanen(judul = "Tanggal Panen", isinya = catatanPanen.tanggalPanen ?: "Tidak tersedia")
            ComponentDetailCatatanPanen(judul = "Jumlah Panen", isinya = catatanPanen.jumlahPanen ?: "Tidak tersedia")
            ComponentDetailCatatanPanen(judul = "Keterangan", isinya = catatanPanen.keterangan ?: "Tidak tersedia")
        }
    }
}

@Composable
fun ComponentDetailCatatanPanen(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
