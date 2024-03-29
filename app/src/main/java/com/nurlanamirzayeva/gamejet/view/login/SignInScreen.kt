package com.nurlanamirzayeva.gamejet.view.login

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.Screens
import com.nurlanamirzayeva.gamejet.ui.theme.green


@Composable
fun SignIn(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_grey))
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 90.dp, bottom = 30.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(45.dp)


    ) {

        Text("GameJet", color = green, fontSize = 32.sp, fontWeight = FontWeight.Medium)

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

            Text(
                "Login",
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 20.dp),
                fontWeight = FontWeight.SemiBold
            )
            Text("Username", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "Enter Username",
                    Modifier.padding(14.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }

            Text("Password", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    )

            ) {
                Text(
                    text = "Enter Password",
                    Modifier.padding(14.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Forgot Password?", color = Color.White, fontSize = 14.sp)

            }


            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {
                Text(
                    "LOGIN",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account?  ", color = Color.White, fontSize = 14.sp)
                Text("Create an account", color = colorResource(id = R.color.sky_blue),fontSize = 14.sp, modifier = Modifier.clickable { navController.navigate(route=Screens.SignUp) })


            }
        }


    }


}