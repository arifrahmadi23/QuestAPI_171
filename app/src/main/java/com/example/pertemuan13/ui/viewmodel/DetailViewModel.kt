package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository
import com.example.pertemuan13.ui.view.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException


sealed class DetailUiState{
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}



fun Mahasiswa.toDetailUiEvent(): InsertUiEvent{
    return InsertUiEvent(
        nim = nim,
        nama = nama,
        alamat = alamat,
        jenisKelamin = jenisKelamin,
        kelas = kelas,
        angkatan = angkatan
    )
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs:MahasiswaRepository):ViewModel(){
    var mhsUiState : DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    init {
        getMhsByNim()
    }

    fun getMhsByNim(){
        viewModelScope.launch {
            mhsUiState = DetailUiState.Loading
            mhsUiState = try {
                DetailUiState.Success(mhs.getMahasiswaByNim(_nim))
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }

        }
    }
}
