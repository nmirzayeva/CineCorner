package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.ApiClient
import com.nurlanamirzayeva.gamejet.network.ApiService
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.ui.components.BottomNavItems
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


                val mainPageViewModel =
                    MainPageViewModel(MainPageRepository(ApiClient.getInstance()))
                Scaffold(bottomBar = {


                        NavigationBar {
                            val navBackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute=navBackEntry?.destination?.route

                            BottomNavItems.entries.forEach{item->
                                NavigationBarItem(
                                    selected =  item.route==currentRoute ,
                                    onClick = { navController.navigate(item.route)},
                                    icon = { Icon(imageVector =item.icon , contentDescription =null ) })
                        }




                    }


                }) {
                    Surface(modifier = Modifier.padding(it)) {
                        MainPageNavGraph(
                            navController = navController, mainPageViewModel = mainPageViewModel
                        )
                    }
                }
            }
        }
    }
}