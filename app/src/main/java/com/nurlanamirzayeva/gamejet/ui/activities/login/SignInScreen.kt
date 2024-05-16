package com.nurlanamirzayeva.gamejet.view.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.activities.login.Screens
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.MainPageActivity
import com.nurlanamirzayeva.gamejet.ui.components.CustomOutlinedTextField
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.green
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SharedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun SignIn(navController: NavHostController, viewModel: RegisterViewModel) {

    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    val signInSuccess = viewModel.signInSuccess.collectAsState()
    val context = LocalContext.current
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    val isValidEmail = remember(email.text) {
        derivedStateOf { viewModel.isEmailValid(email.text) }
    }

    val isValidPassword = remember(password.text) {
        derivedStateOf { viewModel.isPasswordValid(password.text) }
    }



    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = dark_grey)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 30.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(45.dp)


        ) {

            Text("CineCorner", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Medium)

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
                    onValueChange = {
                        email = it
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = black,
                        unfocusedContainerColor = black,
                        unfocusedBorderColor = black,
                        focusedBorderColor = if (isValidEmail.value) Color.Green else Color.Red,
                    ),
                    labelText = "Enter e-mail"
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = black,
                        unfocusedContainerColor = black,
                        unfocusedBorderColor = black,
                        focusedBorderColor = if (isValidPassword.value) Color.Green else Color.Red,
                    ),
                    labelText = "Enter password",
                    icEyeVisibility = true
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Forgot Password?",
                        fontSize = 16.sp,
                        color = sky_blue,
                        modifier = Modifier.clickable { navController.navigate(Screens.ResetEmail) })

                }


                Button(
                    onClick = {

                        viewModel.errorMessage(email.text, password.text, null)?.let {
                            Toast.makeText(
                                context,
                                viewModel.errorMessage(email.text, password.text, null),
                                Toast.LENGTH_SHORT
                            ).show()
                        } ?: run {

                            viewModel.signIn(email.text, password.text)

                        }

                    },


                    colors = ButtonDefaults.buttonColors(
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
                        color = sky_blue,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(route = Screens.SignUp) {
                                popUpTo(Screens.SignUp) { inclusive = true }
                            }
                        })


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


                val intent=Intent(context,MainPageActivity::class.java).apply {
                    putExtra("name",viewModel.userName.value)
                    putExtra("email",viewModel.userEmail.value)
                }
                context.startActivity(intent)

            }


            is NetworkState.Error -> {
                errorMessage =
                    response.errorMessage ?: context.getString(R.string.error_message)

                Toast.makeText(
                    context,
                    errorMessage, Toast.LENGTH_SHORT
                ).show()

                viewModel.reset()


            }

            null -> {}

        }

    }

}


