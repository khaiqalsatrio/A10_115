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
import com.example.a10_115.ui.view.pekerja.HomeScreenPekerja
import com.example.a10_115.ui.view.tanaman.DestinasiEntryTanaman
import com.example.a10_115.ui.view.tanaman.DestinasiHomeTanaman
import com.example.a10_115.ui.view.tanaman.HomeScreenTanaman
import com.example.a10_115.ui.view.tanaman.EntryTanamanScreen  // Pastikan EntryTanamanScreen sudah ada

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplash.route,
        modifier = modifier
    ) {
        composable(DestinasiSplash.route) {
            Splash(
                onPekerjaClick = {
                    navController.navigate(DestinasiHomePekerja.route)
                },
                onTanamanClick = {
                    navController.navigate(DestinasiHomeTanaman.route)
                },
                onCatatanPanenClick = {

                },
                onAktifitasPertanianClick = {

                }
            )
        }

        composable(DestinasiHomePekerja.route) {
            HomeScreenPekerja(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryPekerja.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route) {
                        popUpTo(DestinasiSplash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(DestinasiHomeTanaman.route) {
            HomeScreenTanaman(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryTanaman.route) // Perbaiki navigasi ke halaman insert tanaman
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route) {
                        popUpTo(DestinasiSplash.route) { inclusive = true }
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

        composable(DestinasiEntryTanaman.route) {
            EntryTanamanScreen(  // Pastikan EntryTanamanScreen sudah ada dan digunakan di sini
                navigateBack = {
                    navController.popBackStack(DestinasiHomeTanaman.route, inclusive = false)
                }
            )
        }
    }
}
