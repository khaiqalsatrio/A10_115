package com.example.a10_115.ui.view.tanaman

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
import com.example.a10_115.model.Tanaman
import com.example.a10_115.ui.Navigation.DestinasiNavigasi
import com.example.a10_115.ui.viewModel.PenyediaViewModel

object DestinasiHomeTanaman : DestinasiNavigasi {
    override val route = "home_tanaman"
    override val titleRes = "Data Tanaman"
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Tanaman")
            }
        },
        topBar = {
            SmallTopAppBar(
                title = { Text("Daftar Data Tanaman") },
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
                    IconButton(onClick = { viewModel.getTanaman() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.tanamanUIState,
            retryAction = { viewModel.getTanaman() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { tanaman ->
                viewModel.deleteTanaman(tanaman.idTanaman) // Panggil fungsi delete
                viewModel.getTanaman() // Refresh daftar tanaman setelah delete
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is HomeUiState.Success -> {
            if (homeUiState.tanaman.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tanaman", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                TanamanLayout(
                    tanaman = homeUiState.tanaman,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idTanaman) },
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
fun TanamanLayout(
    tanaman: List<Tanaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Tanaman) -> Unit,
    onDeleteClick: (Tanaman) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tanaman) { tanaman ->
            TanamanCard(
                tanaman = tanaman,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(tanaman) },
                onDeleteClick = { onDeleteClick(tanaman) }
            )
        }
    }
}

@Composable
fun TanamanCard(
    tanaman: Tanaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Tanaman) -> Unit = {}
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
                    contentDescription = "Tanaman Icon",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = tanaman.idTanaman,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(tanaman) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Tanaman",
                    )
                }
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            Row {
                Text("Id Tanaman :", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(tanaman.idTanaman, style = MaterialTheme.typography.titleMedium)
            }
            Row {
                Text("nama tamanan :", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(tanaman.namaTanaman, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
            Row {
                Text("Periode Tanaman:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(tanaman.periodeTanaman, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                Text("Deskripsi Tanaman:", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(80.dp))
                Text(tanaman.deskripsiTanaman, style = MaterialTheme.typography.bodyMedium)
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