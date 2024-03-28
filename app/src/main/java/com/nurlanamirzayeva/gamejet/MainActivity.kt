package com.nurlanamirzayeva.gamejet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nurlanamirzayeva.gamejet.network.repositories.SignUpRepository
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.view.SignIn
import com.nurlanamirzayeva.gamejet.view.SignUp
import com.nurlanamirzayeva.gamejet.viewmodel.SignUpViewModel

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            GameJetTheme {
                val signUpViewModel = SignUpViewModel(repository = SignUpRepository())
                LoginNavGraph(navController = navController, signUpViewModel = signUpViewModel)
            }
        }
    }
}


