package com.nurlanamirzayeva.gamejet.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.Screens
import com.nurlanamirzayeva.gamejet.ui.components.CustomOutlinedTextField
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.green
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.CONFIRM_PASSWORD
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import com.nurlanamirzayeva.gamejet.utils.USERNAME
import com.nurlanamirzayeva.gamejet.viewmodel.SignUpViewModel

@Composable
fun SignUp(navController: NavHostController, signUpViewModel: SignUpViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    var username by remember { mutableStateOf(TextFieldValue()) }
    val signUpSuccess = signUpViewModel.signUpSuccess.collectAsState()
    val context = LocalContext.current
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
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
                "Create a new account",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "Enter your data to create an account for getting",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                "new friends and news",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 28.dp)
            ) {
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
                    "Username",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )

                CustomOutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    labelText = "Enter an username"
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
                    labelText = "**********",
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(painter = if (passwordVisible) painterResource(R.drawable.eye_low_solid) else painterResource(
                            id = R.drawable.eye_solid
                        ),
                            contentDescription = null,
                            modifier = Modifier
                                .height(60.dp)
                                .clickable { passwordVisible = !passwordVisible })
                    }

                )



                Text(
                    text = "Confirm password",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                CustomOutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    labelText = "**********",
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(painter = if (confirmPasswordVisible) painterResource(R.drawable.eye_low_solid) else painterResource(
                            id = R.drawable.eye_solid
                        ),
                            contentDescription = null,
                            modifier = Modifier
                                .height(60.dp)
                                .clickable { confirmPasswordVisible = !confirmPasswordVisible })
                    }


                )

            }

            Button(
                onClick = {

                    signUpViewModel.signUp(
                        context, userMap = hashMapOf(
                            EMAIL to email.text,
                            PASSWORD to password.text,
                            CONFIRM_PASSWORD to confirmPassword.text,
                            USERNAME to username.text
                        )
                    )

                }, colors = ButtonDefaults.buttonColors(
                    containerColor = green
                ), shape = RoundedCornerShape(8.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)


            ) {
                Text(
                    "SIGN UP",
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
                Text("Already have an account?  ", color = Color.White, fontSize = 16.sp)
                Text(
                    "Log in",
                    color = sky_blue,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { navController.navigate(Screens.SignIn) })


            }
        }

        when (val response = signUpSuccess.value) {

            is NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }

            is NetworkState.Success -> {
                Toast.makeText(context, "Sign up successful! Please log in.", Toast.LENGTH_SHORT)
                    .show()
                navController.navigate(Screens.SignIn)
                signUpViewModel.reset()
            }

            is NetworkState.Error -> {
                Toast.makeText(
                    context,
                    response.errorMessage
                        ?: context.getString(R.string.default_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }

            null -> {}
        }

    }


}

