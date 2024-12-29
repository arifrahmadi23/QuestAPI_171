package com.example.pertemuan13.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan13.ui.view.DestinasiDetail
import com.example.pertemuan13.ui.view.DestinasiEntry
import com.example.pertemuan13.ui.view.DestinasiHome
import com.example.pertemuan13.ui.view.DestinasiUpdate
import com.example.pertemuan13.ui.view.DetailScreen
import com.example.pertemuan13.ui.view.EntryMhsScreen
import com.example.pertemuan13.ui.view.HomeScreen
import com.example.pertemuan13.ui.view.UpdateScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // HomeScreen composable
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }

        // EntryMhsScreen composable
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        // DetailScreen composable with argument
        composable(DestinasiDetail.routeWithArg) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString(DestinasiDetail.NIM) ?: ""
            DetailScreen(
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    // Navigasi menuju halaman update
                    navController.navigate("${DestinasiUpdate.route}/$nim")
                }
            )
        }

        // UpdateScreen composable with argument (for update)
        composable(DestinasiUpdate.routeWithArg) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString(DestinasiUpdate.NIM) ?: ""
            UpdateScreen(
                navigateBack = { navController.popBackStack() },
                onNavigate = {
                    // Jika diperlukan, jalankan fungsi lain saat navigasi selesai
                    navController.navigate(DestinasiHome.route)
                }
            )
        }
    }
}
