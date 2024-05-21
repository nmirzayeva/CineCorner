package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.paging.SearchPagingSource
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import retrofit2.Response

@Composable
fun MainPageNavGraph(
    navController: NavHostController,
    mainPageViewModel: MainPageViewModel,
    settingsViewModel: SettingsViewModel,

) {

    NavHost(navController = navController, startDestination = Screens.MainPage) {
        composable(route = Screens.MainPage) {

            MainPage(mainPageViewModel = mainPageViewModel, navController = navController)
        }

        composable(route = Screens.ViewAllDiscover) {

            ViewAllDiscoverScreen(mainPageViewModel = mainPageViewModel,navController=navController)

        }
        composable(route = Screens.ViewAllTrending) {

            ViewAllTrendingScreen(mainPageViewModel = mainPageViewModel,navController=navController)
        }
        composable(route=Screens.Profile){
            ProfileScreen(navController=navController,mainPageViewModel=mainPageViewModel)
        }
        composable(route=Screens.Detail){
            DetailScreen(mainPageViewModel=mainPageViewModel,navController=navController)
        }

        composable(route=Screens.DarkMode){
            DarkModeScreen(settingsViewModel = settingsViewModel )
        }
        composable(route=Screens.Favorite){
            FavoriteScreen(mainPageViewModel=mainPageViewModel)
        }
        composable(route=Screens.Search){
            SearchMoviesScreen(mainPageViewModel = mainPageViewModel,navController=navController)
        }

    }
}

object Screens {
    const val MainPage ="MainPage"
    const val Profile= "Profile"
    const val ViewAllDiscover = "ViewAllDiscover"
    const val ViewAllTrending = "ViewAllTrending"
    const val Detail="Detail"
    const val DarkMode="DarkMode"
    const val Favorite="Favorite"
    const val Search="Search"
}


