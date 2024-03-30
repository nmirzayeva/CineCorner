package com.nurlanamirzayeva.gamejet.view.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey


@Composable
fun MainPage(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dark_grey),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Main Page", fontSize = 40.sp, color = Color.White)

    }

}