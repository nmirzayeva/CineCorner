package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel

@Composable
fun MainPageNavGraph(
    navController: NavHostController,
    mainPageViewModel: MainPageViewModel
) {

    NavHost(navController = navController, startDestination = Screens.MainPage) {
        composable(route = Screens.MainPage) {

            MainPage(mainPageViewModel = mainPageViewModel, navController = navController)
        }

        composable(route = Screens.ViewAllDiscover) {

            ViewAllDiscoverScreen(mainPageViewModel = mainPageViewModel)

        }
        composable(route = Screens.ViewAllTrending) {

            ViewAllTrendingScreen(mainPageViewModel = mainPageViewModel)
        }

    }
}

object Screens {
    const val MainPage = "MainPage"
    const val ViewAllDiscover = "ViewAllDiscover"
    const val ViewAllTrending = "ViewAllTrending"
}


