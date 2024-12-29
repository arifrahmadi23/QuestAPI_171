package com.example.pertemuan13.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.customwidget.CostumeTopAppBar
import com.example.pertemuan13.navigation.DestinasiNavigasi
import com.example.pertemuan13.ui.viewmodel.PenyediaViewModel
import com.example.pertemuan13.ui.viewmodel.UpdateViewModel
import kotlinx.coroutines.launch

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mhs"
    const val NIM = "nim"
    val routeWithArg = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertUiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            EntryBody(
                insertUiState = insertUiState,
                onMahasiswaValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onSaveClick = {
                    // Mengupdate data mahasiswa
                    coroutineScope.launch {
                        viewModel.updateMahasiswa()  // Pastikan data diperbarui ke repository
                        onNavigate()  // Setelah update, navigasi ke halaman lain
                    }
                }
            )
        }
    }
}
