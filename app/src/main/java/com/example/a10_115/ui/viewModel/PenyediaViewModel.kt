package com.example.a10_115.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_115.PertanianApplications
import com.example.a10_115.ui.viewModel.pekerja.HomePekerjaViewModel
import com.example.a10_115.ui.viewModel.pekerja.insertPekerjaViewModel
import com.example.a10_115.ui.viewModel.tanaman.HomeTanamanViewModel
import com.example.a10_115.ui.viewModel.tanaman.insertTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePekerjaViewModel(
                aplikasiPertanian()
                    .container.pekerjaRepository
            )
        }

        initializer {
            insertPekerjaViewModel(
                aplikasiPertanian()
                    .container.pekerjaRepository
            )
        }

        initializer {
            HomeTanamanViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }

        initializer {
            insertTanamanViewModel(
                aplikasiPertanian()
                    .container.tanamanRepository
            )
        }
    }

    fun CreationExtras.aplikasiPertanian():PertanianApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplications)

}