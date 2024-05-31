package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import com.nurlanamirzayeva.gamejet.ui.components.CustomOutlinedTextField
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.green

import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.Screens
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel

@Composable
fun EditProfileScreen(viewModel:RegisterViewModel,mainPageViewModel: MainPageViewModel,navController:NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var username by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    val changeUserProfile = mainPageViewModel.updateUserProfileResponse.collectAsState()


    val context = LocalContext.current
    val isValidEmail = remember(email.text) {
        derivedStateOf { viewModel.isEmailValid(email.text) }
    }

    val isFocusedEmail = remember {
        mutableStateOf(false)
    }

    val isValidPassword = remember(password.text) {
        derivedStateOf { viewModel.isPasswordValid(password.text) }
    }
    val isFocusedPassword = remember {
        mutableStateOf(false)
    }
    val isValidConfirmPassword = remember(confirmPassword.text) {
        derivedStateOf { viewModel.isConfirmPasswordValid(confirmPassword.text) }

    }

    Box {
        Column(modifier = Modifier.run {
            fillMaxSize()
                .background(color = dark_grey)
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 30.dp)


        }

        ) {
            Text(
                "Edit Your Profile",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 28.dp)
            ) {
                Text(
                    "New e-mail",
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
                    labelText = "Enter new e-mail",
                    onFocused = { isFocusedEmail.value = it }
                )

                AnimatedVisibility(visible = isFocusedEmail.value) {
                    Text(
                        text = "Enter a valid e-mail",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (!isValidEmail.value) Color.Red else Color.Green
                    )
                }


                Text(
                    "New username",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )

                CustomOutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    labelText = "Enter new  username"
                )



                Text(
                    " New Password",
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
                    labelText = "**********",
                    onFocused = { isFocusedPassword.value = it },
                    icEyeVisibility = true
                )
                AnimatedVisibility(visible = isFocusedPassword.value) {

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "The password must be at least 8 characters long ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isValidPassword.value) Color.Red else Color.Green
                        )
                        Text(
                            text = "The password must contain at least one lowercase letter ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isValidPassword.value) Color.Red else Color.Green
                        )
                        Text(
                            text = "The password must contain at least one uppercase letter  ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isValidPassword.value) Color.Red else Color.Green
                        )
                        Text(
                            text = "The password must contain at least one number ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isValidPassword.value) Color.Red else Color.Green
                        )
                        Text(
                            text = "The password must contain at least one special character ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (!isValidPassword.value) Color.Red else Color.Green
                        )
                    }
                }



                Text(
                    text = "Confirm password",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                CustomOutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = black,
                        unfocusedContainerColor = black,
                        unfocusedBorderColor = black,
                        focusedBorderColor = if (isValidConfirmPassword.value) Color.Green else Color.Red,
                    ),
                    labelText = "**********",
                    icEyeVisibility = true
                )


            }

            Button(
                onClick = {
                    viewModel.errorMessage(email.text, password.text, confirmPassword.text)?.let {
                        // show error toast
                        Toast.makeText(context, viewModel.errorMessage(email.text,password.text,confirmPassword.text), Toast.LENGTH_SHORT).show()
                    } ?: run {
                        // perform the task
                        mainPageViewModel.updateUserProfile(
                            context=context,
                            name = username.text,
                            email=email.text,
                            newPassword = password.text,
                            newConfirmPassword = confirmPassword.text
                        )
                    }


                }, colors = ButtonDefaults.buttonColors(
                    containerColor = green
                ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)


            ) {
                Text(
                    "Change",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }


        }

        when (val response = changeUserProfile.value) {

            is NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp), color = Color.White

                )
            }

            is NetworkState.Success -> {
                Toast.makeText(context, "Successfully changed Profile ", Toast.LENGTH_SHORT)
                    .show()

                navController.navigate(Screens.Profile) {

                    popUpTo(Screens.Profile) { inclusive = true }
                }
                mainPageViewModel.resetEditProfile()
            }

            is NetworkState.Error -> {


                Toast.makeText(context, "Invalid email or password ->${response.errorMessage}", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "EditProfileScreen: ${response.errorMessage}")
                mainPageViewModel.resetEditProfile()
            }

            null -> {}
        }

    }

}