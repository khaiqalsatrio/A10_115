package com.example.a10_115.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a10_115.PertanianApplications
import com.example.a10_115.ui.viewModel.pekerja.HomeViewModel
import com.example.a10_115.ui.viewModel.pekerja.insertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiPertanian()
                .container.pekerjaRepository)
        }

        initializer {
            insertViewModel(aplikasiPertanian()
                .container.pekerjaRepository)
        }

        initializer {
            HomeViewModel(aplikasiPertanian()
                .container.tanamanRepository)
        }
    }

    fun CreationExtras.aplikasiPertanian():PertanianApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplications)

}