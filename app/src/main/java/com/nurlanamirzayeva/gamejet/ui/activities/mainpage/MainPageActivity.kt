package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.ApiClient
import com.nurlanamirzayeva.gamejet.network.ApiService
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel

class MainPageActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page2)

        setContent {
            navController = rememberNavController()
            GameJetTheme {

                val mainPageViewModel = MainPageViewModel(MainPageRepository(ApiClient.getInstance()))
                Surface {
                    MainPageNavGraph(
                        navController = navController, mainPageViewModel = mainPageViewModel
                    )
                }
            }
        }
    }
}