package com.example.a10_115.ui.view.catatanPanen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a10_115.model.CatatanPanen
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.catatanPanen.HomeCatatanViewModel
import com.example.a10_115.ui.viewModel.catatanPanen.HomeUiState

object DestinasiHomeCatatan : DestinasiNavigasi {
    override val route = "home_catatan"
    override val titleRes = "Data Catatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCatatanPanen(
    navigateToItemEntry: () -> Unit,
    navigateToSplash: () -> Unit, // Tambahkan parameter untuk kembali ke Splash
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomeCatatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Catatan")
            }
        },
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Data Catatan panen") },
                navigationIcon = { // Tambahkan ikon panah di sebelah kiri
                    IconButton(onClick = navigateToSplash) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Ganti dengan ikon panah kiri
                            contentDescription = "Kembali ke Splash"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = { viewModel.getCatatanPanen() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.catatanPanenUIState,
            retryAction = { viewModel.getCatatanPanen() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { catatanPanen ->
                viewModel.deleteCatatanPanen(catatanPanen.idPanen) // Panggil fungsi delete
                viewModel.getCatatanPanen() // Refresh daftar catatan panen setelah delete
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (CatatanPanen) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomeUiState.Success -> {
            if (homeUiState.catatanPanen.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data catatan panen", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                CatatanPanenLayout(
                    catatanPanen = homeUiState.catatanPanen,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPanen) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun CatatanPanenLayout(
    catatanPanen: List<CatatanPanen>,
    modifier: Modifier = Modifier,
    onDetailClick: (CatatanPanen) -> Unit,
    onDeleteClick: (CatatanPanen) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(catatanPanen) { catatanPanen ->
            CatatanPanenCard(
                catatanPanen = catatanPanen,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(catatanPanen) },
                onDeleteClick = { onDeleteClick(catatanPanen) }
            )
        }
    }
}

@Composable
fun CatatanPanenCard(
    catatanPanen: CatatanPanen,
    modifier: Modifier = Modifier,
    onDeleteClick: (CatatanPanen) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Catatan Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = catatanPanen.idPanen,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(catatanPanen) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Catatan panen",
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            Row {
                Text("ID Panen:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(catatanPanen.idPanen, style = MaterialTheme.typography.titleMedium)
            }
            Row {
                Text("ID Tanaman:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(catatanPanen.idTanaman, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
            Row {
                Text("Tanggal Panen:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(catatanPanen.tanggalPanen, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                Text("Jumlah Panen:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(catatanPanen.jumlahPanen, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                Text("Keterangan:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(catatanPanen.keterangan, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Error Icon",
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}