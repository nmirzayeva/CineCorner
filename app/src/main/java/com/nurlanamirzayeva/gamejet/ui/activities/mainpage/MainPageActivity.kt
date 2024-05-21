package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.Screens
import com.nurlanamirzayeva.gamejet.ui.components.BottomBarTabs
import com.nurlanamirzayeva.gamejet.ui.components.BottomNavItems
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@AndroidEntryPoint
class MainPageActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val mainPageViewModel = hiltViewModel<MainPageViewModel>()
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            navController = rememberNavController()

            GameJetTheme(settingsViewModel) {


                val navBackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackEntry?.destination?.route
                var selectedTabIndex by remember { mutableIntStateOf(1) }
                val hazeState = remember { HazeState() }
                Scaffold(containerColor = dark_grey, bottomBar = {
                    if(currentRoute in listOf(Screens.MainPage,Screens.Profile)) {

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .height(64.dp)
                                .hazeChild(state = hazeState, shape = CircleShape)
                                .border(
                                    width = Dp.Hairline,
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = .8f),
                                            Color.White.copy(alpha = .2f),
                                        )
                                    ),
                                    shape = CircleShape
                                )

                        ) {
                            BottomBarTabs(
                                tabs = BottomNavItems.entries.toList(),
                                selectedTab = selectedTabIndex,
                                onTabSelected = { item ->
                                    if (selectedTabIndex == BottomNavItems.entries.indexOf(item))
                                        return@BottomBarTabs
                                    selectedTabIndex = BottomNavItems.entries.indexOf(item)
                                    navController.navigate(item.route) {
                                        popUpTo(item.route) { inclusive = true }
                                    }

                                }
                            )


                            val animatedSelectedTabIndex by animateFloatAsState(
                                targetValue = selectedTabIndex.toFloat(),
                                label = "animatedSelectedTabIndex",
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                )
                            )

                            val animatedColor by animateColorAsState(
                                targetValue = BottomNavItems.entries[selectedTabIndex].color,
                                label = "animatedColor",
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessLow,
                                )
                            )

                            Canvas(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                            ) {
                                val tabWidth = size.width / BottomNavItems.entries.size
                                drawCircle(
                                    color = animatedColor.copy(alpha = .6f),
                                    radius = size.height / 2,
                                    center = Offset(
                                        (tabWidth * animatedSelectedTabIndex) + tabWidth / 2,
                                        size.height / 2
                                    )
                                )
                            }


                        }
                    }

                }) { padding ->
                    Column(
                        modifier = Modifier
                            .padding()
                            .haze(
                                hazeState,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                tint = Color.Black.copy(alpha = .2f),
                                blurRadius = 30.dp,
                            )
                            .fillMaxSize(),
                    ) {
                        MainPageNavGraph(
                            navController = navController, mainPageViewModel = mainPageViewModel,settingsViewModel=settingsViewModel
                        )
                    }
                }
            }
        }
    }
}

