package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.api.ResourceDescriptor.History
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.paging.SearchPagingSource
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.ActorViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import retrofit2.Response

@Composable
fun MainPageNavGraph(
    navController: NavHostController,
    mainPageViewModel: MainPageViewModel,
    settingsViewModel: SettingsViewModel,
    viewModel: RegisterViewModel,
    actorPageViewModel: ActorViewModel
) {

    NavHost(navController = navController, startDestination = Screens.MainPage) {
        composable(route = Screens.MainPage) {
            MainPage(mainPageViewModel = mainPageViewModel, navController = navController)
            BackHandler(true) {
            }
        }

        composable(route = Screens.ViewAllDiscover) {

            ViewAllDiscoverScreen(mainPageViewModel = mainPageViewModel,navController=navController)

        }
        composable(route = Screens.ViewAllTrending) {

            ViewAllTrendingScreen(mainPageViewModel = mainPageViewModel,navController=navController)
        }
        composable(route = Screens.ViewAllUpcoming) {

            ViewAllUpcomingScreen(mainPageViewModel = mainPageViewModel,navController=navController)
        }
        composable(route=Screens.Profile){
            ProfileScreen(navController=navController,mainPageViewModel=mainPageViewModel)
            BackHandler(true) {
            }
        }
        composable(route=Screens.Detail){
            DetailScreen(mainPageViewModel=mainPageViewModel,navController=navController,actorPageViewModel=actorPageViewModel)
        }

        composable(route=Screens.DarkMode){
            DarkModeScreen(settingsViewModel = settingsViewModel )
        }
        composable(route=Screens.Favorite){
            FavoriteScreen(mainPageViewModel=mainPageViewModel,navController=navController)
            BackHandler(true) {
            }
        }
        composable(route=Screens.Search){
            SearchMoviesScreen(mainPageViewModel = mainPageViewModel,navController=navController)
        }

        composable(route=Screens.History){
            HistoryScreen(mainPageViewModel=mainPageViewModel,navController=navController)
        }
        composable(route=Screens.EditProfile){
            EditProfileScreen(mainPageViewModel=mainPageViewModel, viewModel = viewModel, navController = navController)
        }
        composable(route=Screens.Actors){
            ActorsScreen(actorPageViewModel =actorPageViewModel,mainPageViewModel=mainPageViewModel,navController=navController )
        }
    }
}

object Screens {
    const val MainPage ="MainPage"
    const val Profile= "Profile"
    const val ViewAllDiscover = "ViewAllDiscover"
    const val ViewAllTrending = "ViewAllTrending"
    const val ViewAllUpcoming = "ViewAllUpcoming"
    const val Detail="Detail"
    const val DarkMode="DarkMode"
    const val Favorite="Favorite"
    const val Search="Search"
    const val History="History"
    const val EditProfile="EditProfile"
    const val Actors="Actors"
}


