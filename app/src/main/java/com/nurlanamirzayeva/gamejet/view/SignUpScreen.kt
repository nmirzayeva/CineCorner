package com.nurlanamirzayeva.gamejet.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.Screens
import com.nurlanamirzayeva.gamejet.utils.CONFIRM_PASSWORD
import com.nurlanamirzayeva.gamejet.utils.COUNTRY_CODE
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import com.nurlanamirzayeva.gamejet.viewmodel.SignUpViewModel


@Composable
fun SignUp(navController: NavHostController,signUpViewModel: SignUpViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+1") }
    val errorMessage = signUpViewModel.errorMessage.collectAsState()
    val signUpSuccess = signUpViewModel.signUpSuccess.collectAsState()
    val context = LocalContext.current



        when (signUpSuccess.value) {
            true -> {
                Toast.makeText(context, "Sign up successful! Please log in.", Toast.LENGTH_SHORT)
                    .show()
                navController.navigate(Screens.SignIn)
                signUpViewModel.reset()
            }

            false -> {
                Toast.makeText(context, "$errorMessage", Toast.LENGTH_SHORT).show()
            }

            null -> {

            }
        }





    Column(modifier = Modifier.run {
        fillMaxSize()
            .background(color = colorResource(id = R.color.dark_grey))
            .padding(start = 16.dp, end = 16.dp, top = 80.dp, bottom = 30.dp)


    }

    ) {
        Text(
            "Create a new account",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            "Enter your data to create an account for",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            "getting new friends and news",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(vertical = 30.dp)
        ) {
            Text("E-mail", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    ),
                label = { Text("Enter e-mail") }
            )
            Text("Country", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            TextField(
                value = countryCode,
                onValueChange = { countryCode = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    ),
                label = { Text("Enter password") }
            )

            Text("Password", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    ),
                label = { Text("**********") },
                visualTransformation = PasswordVisualTransformation()
            )
            Text("Confirm ", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(
                        colorResource(id = R.color.soft_dark),
                        shape = RoundedCornerShape(8.dp)
                    ),
                label = { Text("**********") },
                visualTransformation = PasswordVisualTransformation()
            )
        }





        Button(
            onClick = {

                signUpViewModel.signUp(userMap= hashMapOf(EMAIL to email, PASSWORD to password,
                    CONFIRM_PASSWORD to confirmPassword, COUNTRY_CODE to countryCode))



                      }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.green
                )
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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text("Already have an account?  ", color = Color.White, fontSize = 14.sp)
            Text(
                "Log in",
                color = colorResource(id = R.color.sky_blue),
                fontSize = 14.sp,
                modifier = Modifier.clickable { navController.navigate(Screens.SignIn) })


        }
    }

}

