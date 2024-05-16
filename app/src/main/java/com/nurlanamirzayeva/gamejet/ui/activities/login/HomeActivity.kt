package com.nurlanamirzayeva.gamejet.view.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.ui.activities.login.LoginNavGraph
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @Inject
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<RegisterViewModel>()
            val settingsViewModel= hiltViewModel<SettingsViewModel>()
            navController = rememberNavController()
            GameJetTheme(settingsViewModel) {

                Surface {
                    LoginNavGraph(
                        navController = navController,
                        viewModel=viewModel
                    )
                }
            }
        }
    }
}


