package com.example.pertemuan13.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.customwidget.CostumeTopAppBar
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.navigation.DestinasiNavigasi
import com.example.pertemuan13.ui.viewmodel.DetailUiState
import com.example.pertemuan13.ui.viewmodel.DetailViewModel
import com.example.pertemuan13.ui.viewmodel.PenyediaViewModel


object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mhs"
    const val NIM = "nim"
    val routeWithArg = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { }
) {
    // Scaffold untuk mengatur tampilan atas dan FAB
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel.mhsUiState.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Mahasiswa")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Padding dari innerPadding
                .padding(16.dp) // Padding tambahan agar lebih rapi
        ) {
            when (val state = viewModel.mhsUiState) {
                is DetailUiState.Loading -> {
                    // Tampilkan indikator loading jika data sedang diambil
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiState.Error -> {
                    // Tampilkan pesan error jika terjadi kesalahan
                    Text("Error loading data", modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiState.Success -> {
                    // Tampilkan data mahasiswa jika berhasil
                    ItemDetailMhs(mahasiswa = state.mahasiswa)
                }
            }
        }
    }
}


@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
) {
    // Menggunakan Card untuk menampilkan data mahasiswa
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
        }
    }
}



@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,  // Menghapus bold
                color = MaterialTheme.colorScheme.onSurface  // Menyesuaikan warna teks
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,  // Menghapus bold
                color = MaterialTheme.colorScheme.onSurface  // Menggunakan warna standar untuk teks
            )
        )
    }
}
