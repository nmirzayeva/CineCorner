package com.nurlanamirzayeva.gamejet.view.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.LoginNavGraph
import com.nurlanamirzayeva.gamejet.network.repositories.SignInRepository
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.viewmodel.SignInViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            GameJetTheme {
                val signUpViewModel = hiltViewModel<SignUpViewModel>()

                val signInViewModel = SignInViewModel(repository = SignInRepository(auth = auth))
                Surface {
                    LoginNavGraph(
                        navController = navController,
                        signUpViewModel = signUpViewModel,
                        signInViewModel = signInViewModel
                    )
                }
            }
        }
    }
}


