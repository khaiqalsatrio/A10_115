package com.example.a10_115.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a10_115.ui.view.Splash.DestinasiSplash
import com.example.a10_115.ui.view.Splash.Splash
import com.example.a10_115.ui.view.catatanPanen.DestinasiEntryCatatan
import com.example.a10_115.ui.view.catatanPanen.DestinasiHomeCatatan
import com.example.a10_115.ui.view.catatanPanen.EntryCatatanScreen
import com.example.a10_115.ui.view.catatanPanen.HomeScreenCatatanPanen
import com.example.a10_115.ui.view.pekerja.DestinasiEntryPekerja
import com.example.a10_115.ui.view.pekerja.DestinasiHomePekerja
import com.example.a10_115.ui.view.pekerja.EntryPekerjaScreen
import com.example.a10_115.ui.view.pekerja.HomeScreenPekerja
import com.example.a10_115.ui.view.tanaman.DestinasiEntryTanaman
import com.example.a10_115.ui.view.tanaman.DestinasiHomeTanaman
import com.example.a10_115.ui.view.tanaman.EntryTanamanScreen
import com.example.a10_115.ui.view.tanaman.HomeScreenTanaman

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
        // Splash Screen
        composable(DestinasiSplash.route) {
            Splash(
                onPekerjaClick = {
                    navController.navigate(DestinasiHomePekerja.route) {
                        popUpTo(DestinasiSplash.route) {
                            inclusive = true
                        }
                    }
                },
                onTanamanClick = {
                    navController.navigate(DestinasiHomeTanaman.route) {
                        popUpTo(DestinasiSplash.route) {
                            inclusive = true
                        }
                    }
                },
                onCatatanPanenClick = {
                    navController.navigate(DestinasiHomeCatatan.route) {
                        popUpTo(DestinasiSplash.route) {
                            inclusive = true
                        }
                    }
                },
                onAktifitasPertanianClick = {
                    // Implementasikan jika diperlukan
                }
            )
        }

        // Halaman Home Pekerja
        composable(DestinasiHomePekerja.route) {
            HomeScreenPekerja(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryPekerja.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Pekerja
        composable(DestinasiEntryPekerja.route) {
            EntryPekerjaScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }

        // Halaman Home Tanaman
        composable(DestinasiHomeTanaman.route) {
            HomeScreenTanaman(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryTanaman.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Tanaman
        composable(DestinasiEntryTanaman.route) {
            EntryTanamanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }

        // Halaman Home Catatan
        composable(DestinasiHomeCatatan.route) {
            HomeScreenCatatanPanen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryCatatan.route)
                },
                navigateToSplash = {
                    navController.navigate(DestinasiSplash.route)
                }
            )
        }
        // Halaman Entry Catatan
        composable(DestinasiEntryCatatan.route) {
            EntryCatatanScreen(
                navigateBack = {
                    navController.popBackStack() // Kembali ke halaman sebelumnya
                }
            )
        }
    }
}


