package com.example.a10_115.ui.view.tanaman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.model.Tanaman
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.costumWidget.CostumeTopAppBar
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.tanaman.DetailTanamanUiState
import com.example.a10_115.ui.viewModel.tanaman.DetailTanamanViewModel

object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "detail_tanaman"
    override val titleRes = "Detail Tanaman"
    const val idTanaman = "idTanaman"
    val routeWithArgs = "$route/{$idTanaman}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTanamanView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailTanamanViewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val detailUiState = detailTanamanViewModel.detailTanamanUiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val idTanaman = (detailUiState as? DetailTanamanUiState.Success)?.tanaman?.idTanaman
                    if (idTanaman != null) {
                        onEditClick(idTanaman)
                    } else {
                        println("Error: ID Tanaman not available")
                    }
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tanaman"
                )
            }
        }
    ) { innerPadding ->
        DetailTanamanContent(
            dtlTanamanUiState = detailUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDeleteClick = {
                detailTanamanViewModel.deleteTanaman()
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailTanamanContent(
    dtlTanamanUiState: DetailTanamanUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (dtlTanamanUiState) {
        is DetailTanamanUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailTanamanUiState.Error -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        is DetailTanamanUiState.Success -> {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DetailTanamanCard(tanaman = dtlTanamanUiState.tanaman)
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hapus Tanaman")
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
fun DetailTanamanCard(
    tanaman: Tanaman,
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
            ComponentDetailTanaman(judul = "ID Tanaman", isinya = tanaman.idTanaman ?: "Tidak tersedia")
            ComponentDetailTanaman(judul = "Nama Tanaman", isinya = tanaman.namaTanaman ?: "Tidak tersedia")
            ComponentDetailTanaman(judul = "Periode Tanam", isinya = tanaman.periodeTanaman ?: "Tidak tersedia")
            ComponentDetailTanaman(judul = "Deskripsi Tanaman", isinya = tanaman.deskripsiTanaman ?: "Tidak tersedia")
        }
    }
}

@Composable
fun ComponentDetailTanaman(
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