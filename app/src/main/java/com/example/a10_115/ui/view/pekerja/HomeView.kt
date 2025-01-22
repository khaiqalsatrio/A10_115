package com.example.a10_115.ui.view.pekerja

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import com.example.a10_115.model.Pekerja
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.viewModel.PenyediaViewModel
import com.example.a10_115.ui.viewModel.pekerja.HomeUiState
import com.example.a10_115.ui.viewModel.pekerja.HomeViewModel

object DestinasiHomePekerja : DestinasiNavigasi {
    override val route = "home_pekerja"
    override val titleRes = "Data Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToSplash: () -> Unit, // Tambahkan parameter untuk kembali ke Splash
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onRefresh: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Pekerja")
            }
        },
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Data Pekerja") },
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
                    IconButton(onClick = { viewModel.getPekerja() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.pekerjaUIState,
            retryAction = { viewModel.getPekerja() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { pekerja ->
                viewModel.deletePekerja(pekerja.idPekerja) // Panggil fungsi delete
                viewModel.getPekerja() // Refresh daftar pekerja setelah delete
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomeUiState.Success -> {
            if (homeUiState.pekerja.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pekerja", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                PekerjaLayout(
                    pekerja = homeUiState.pekerja,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPekerja) },
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
fun PekerjaLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit,
    onDeleteClick: (Pekerja) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pekerja) { pekerja ->
            PekerjaCard(
                pekerja = pekerja,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pekerja) },
                onDeleteClick = { onDeleteClick(pekerja) }
            )
        }
    }
}

@Composable
fun PekerjaCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {}
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
                    imageVector = Icons.Default.Person,
                    contentDescription = "Pekerja Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = pekerja.idPekerja,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pekerja) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Pekerja",
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            Row {
                Text("Nama:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(pekerja.namaPekerja, style = MaterialTheme.typography.titleMedium)
            }
            Row {
                Text("Jabatan:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(pekerja.jabatan, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
            Row {
                Text("Kontak:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(pekerja.kontakPekerja, style = MaterialTheme.typography.bodyMedium)
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
            imageVector = Icons.Default.Home,
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