package com.nurlanamirzayeva.gamejet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nurlanamirzayeva.gamejet.view.login.SignIn
import com.nurlanamirzayeva.gamejet.view.login.SignUp
import com.nurlanamirzayeva.gamejet.viewmodel.SignUpViewModel


@Composable

fun LoginNavGraph(navController: NavHostController,signUpViewModel: SignUpViewModel) {

    NavHost(navController = navController , startDestination = Screens.SignIn ){
        composable(route="SignIn"){

          SignIn(navController=navController)


        }
        composable(route="SignUp"){

            SignUp(navController=navController,signUpViewModel=signUpViewModel)
        }


    }

}

    object Screens {
        const val SignIn = "SignIn"
        const val SignUp = "SignUp"

    }