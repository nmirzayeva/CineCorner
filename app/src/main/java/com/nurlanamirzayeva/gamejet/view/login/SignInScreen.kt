package com.nurlanamirzayeva.gamejet.view.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.Screens
import com.nurlanamirzayeva.gamejet.ui.components.CustomOutlinedTextField
import com.nurlanamirzayeva.gamejet.ui.theme.GameJetTheme
import com.nurlanamirzayeva.gamejet.ui.theme.green
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.view.mainpage.MainPage
import com.nurlanamirzayeva.gamejet.viewmodel.SignInViewModel


@Composable
fun SignIn(navController: NavHostController, signInViewModel: SignInViewModel) {

    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    val signInSuccess = signInViewModel.signInSuccess.collectAsState()
    val context = LocalContext.current

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_grey))
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 30.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(45.dp)


        ) {

            Text("GameJet", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Medium)

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Text(
                    "Log In",
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "E-mail",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                CustomOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    labelText = "Enter e-mail",

                    )

                Text(
                    "Password",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                CustomOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    labelText = "Enter password",
                    visualTransformation = PasswordVisualTransformation()

                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Forgot Password?", color = Color.White, fontSize = 16.sp)

                }


                Button(
                    onClick = {
                        signInViewModel.signIn(email.text, password.text)


                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = green
                    ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)

                ) {
                    Text(
                        "LOGIN",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp), horizontalArrangement = Arrangement.Center
                ) {
                    Text("Don't have an account?  ", color = Color.White, fontSize = 16.sp)
                    Text(
                        "Create an account",
                        color = colorResource(id = R.color.sky_blue),
                        fontSize = 16.sp,
                        modifier = Modifier.clickable { navController.navigate(route = Screens.SignUp) })


                }
            }
        }

        when (val response = signInSuccess.value) {

            is NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }


            is NetworkState.Success -> {

                navController.navigate(Screens.MainPage)
            }


            is NetworkState.Error -> {

                Toast.makeText(
                    context,
                    response.errorMessage
                        ?: context.getString(R.string.default_error_message), Toast.LENGTH_SHORT
                ).show()


            }

            null -> {}

        }

    }
}
