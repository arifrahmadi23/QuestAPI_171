package com.example.pertemuan13.ui.viewmodel

import com.example.pertemuan13.model.Mahasiswa

class InsertViewModel {
}

data class InsertUiEvent(
    val nim: String ="",
    val nama: String ="",
    val alamat: String ="",
    val jenisKelamin: String ="",
    val kelas: String ="",
    val angkatan: String =""

)



