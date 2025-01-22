package com.example.a10_115.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a10_115.ui.view.Splash.DestinasiSplash
import com.example.a10_115.ui.view.Splash.Splash
import com.example.a10_115.ui.view.pekerja.DestinasiEntryPekerja
import com.example.a10_115.ui.view.pekerja.DestinasiHomePekerja
import com.example.a10_115.ui.view.pekerja.EntryPekerjaScreen
import com.example.a10_115.ui.view.pekerja.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController(), modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route, // Halaman Splash sebagai awal
        modifier = Modifier
    ) {
        composable(DestinasiSplash.route) {
            Splash(
                onDosenClick = {
                    navController.navigate(DestinasiHomePekerja.route)
                },
                onMataKuliahClick = {
                    navController.navigate(DestinasiEntryPekerja.route)
                }
            )
        }

        composable(DestinasiHomePekerja.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryPekerja.route)
                },
                navigateToSplash = { // Navigasi kembali ke Splash
                    navController.navigate(DestinasiSplash.route) {
                        popUpTo(DestinasiSplash.route) { inclusive = true } // Hapus riwayat sebelumnya
                    }
                }
            )
        }

        composable(DestinasiEntryPekerja.route) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.popBackStack(DestinasiHomePekerja.route, inclusive = false)
                }
            )
        }
    }
}


