package com.nurlanamirzayeva.gamejet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.Screens

enum class BottomNavItems(val route: String, val icon: ImageVector, val label: String) {
    Home(Screens.MainPage, Icons.Default.Home, "Home"),
    Profile (Screens.ViewAllTrending, Icons.Default.Person, "Profile")

}