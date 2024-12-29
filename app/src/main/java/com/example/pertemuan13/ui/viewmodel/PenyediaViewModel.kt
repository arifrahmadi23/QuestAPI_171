package com.example.pertemuan13.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.decode.BitmapFactoryDecoder
import com.example.pertemuan13.MahasiswaApplications
import okhttp3.EventListener

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer { InsertViewModel(aplikasiMahasiswa().container.mahasiswaRepository) }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            DetailViewModel(
                savedStateHandle = savedStateHandle,
                mhs = aplikasiMahasiswa().container.mahasiswaRepository
            )
        }

    }
}

fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)