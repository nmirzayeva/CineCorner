package com.nurlanamirzayeva.gamejet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nurlanamirzayeva.gamejet.view.login.ResetEmail
import com.nurlanamirzayeva.gamejet.view.login.SignIn
import com.nurlanamirzayeva.gamejet.view.login.SignUp
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel


@Composable

fun LoginNavGraph(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {

    NavHost(navController = navController, startDestination = Screens.SignIn) {
        composable(route = "SignIn") {

            SignIn(navController = navController,viewModel=viewModel )


        }
        composable(route = "SignUp") {

            SignUp(navController = navController, viewModel=viewModel)
        }
        composable(route = "MainPage") {

            MainPage(navController = navController)
        }

        composable(route="ResetEmail"){
            
            ResetEmail(navController=navController, viewModel = viewModel)
        }


    }

}

object Screens {
    const val SignIn = "SignIn"
    const val SignUp = "SignUp"
    const val MainPage = "MainPage"
    const val ResetEmail="ResetEmail"


}