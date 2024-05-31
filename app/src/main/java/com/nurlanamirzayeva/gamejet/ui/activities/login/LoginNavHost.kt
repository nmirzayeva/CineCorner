package com.nurlanamirzayeva.gamejet.ui.activities.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.view.login.ResetEmail
import com.nurlanamirzayeva.gamejet.view.login.SignIn
import com.nurlanamirzayeva.gamejet.view.login.SignUp
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel


@Composable

fun LoginNavGraph(
    navController: NavHostController,
    viewModel: RegisterViewModel,
    auth:FirebaseAuth,
    skipSplash:Boolean
) {

    NavHost(navController = navController, startDestination = if(skipSplash)Screens.SignIn else  Screens.Splash) {
        composable(route="Splash"){
            SplashScreen(navController = navController, auth =auth  )
        }

        composable(route = "SignIn") {

            SignIn(navController = navController,viewModel=viewModel )

        }

        composable(route = "SignUp") {

            SignUp(navController = navController, viewModel=viewModel)
        }


        composable(route="ResetEmail"){
            
            ResetEmail(navController=navController, viewModel = viewModel)
        }


    }

}

object Screens {
    const val SignIn = "SignIn"
    const val SignUp = "SignUp"
    const val ResetEmail= "ResetEmail"
    const val Splash= "Splash"
}