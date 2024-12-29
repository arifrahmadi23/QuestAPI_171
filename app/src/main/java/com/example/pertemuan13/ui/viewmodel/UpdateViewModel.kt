package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository
import com.example.pertemuan13.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
): ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            val mahasiswa = mahasiswaRepository.getMahasiswaByNim(_nim)
            uiState = mahasiswa.toUiStateMhsUpdate() // Mengonversi Mahasiswa menjadi InsertUiState
        }
    }

    // Fungsi update untuk mengirimkan data mahasiswa ke repository
    fun updateMahasiswa() {
        viewModelScope.launch {
            try {
                // Mengonversi InsertUiEvent menjadi Mahasiswa
                val mahasiswa = uiState.insertUiEvent.toMhs()
                mahasiswaRepository.updateMahasiswa(_nim, mahasiswa)  // Menyertakan nim dan mahasiswa
            } catch (e: Exception) {
                e.printStackTrace()  // Tangani jika ada error dalam update
            }
        }
    }

    // Fungsi untuk memperbarui state sesuai dengan event update
    fun updateState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }
}


fun Mahasiswa.toUiStateMhsUpdate(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent()
)