package com.nurlanamirzayeva.gamejet.view.login

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.components.CustomOutlinedTextField
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.green
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.RegisterViewModel


@Composable
fun ResetEmail(viewModel: RegisterViewModel, navController: NavHostController) {

    val resetPasswordState by viewModel.resetPasswordSuccess.collectAsState()
    var email by remember {
        mutableStateOf(TextFieldValue())
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dark_grey)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 150.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text = "Reset your password",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {

                Text(
                    text = "Enter your username or email address and we",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "will send a link to reset your password",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                labelText = "Enter e-mail"
            )
            Button(
                onClick = {

                    if (viewModel.isEmailValid(email.text)) {
                        viewModel.resetPassword(email.text)

                    } else {
                        if (email.text.isEmpty()) {
                            Toast.makeText(
                                context,
                                R.string.reset_pass_empty_email_error_message,
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                context,
                                R.string.reset_pass_error_message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                },
                colors = ButtonDefaults.buttonColors(containerColor = green),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "SEND",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text("If you didn't receive a link ", color = Color.White, fontSize = 16.sp)
                Text(
                    "Resend",
                    color = sky_blue,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        if (viewModel.isEmailValid(email.text)) {
                            viewModel.resetPassword(email.text)

                        } else {
                            if (email.text.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    R.string.reset_pass_empty_email_error_message,
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                Toast.makeText(
                                    context,
                                    R.string.reset_pass_error_message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                    }
                )


            }

        }


        when (resetPasswordState) {

            is NetworkState.Success -> {

                Toast.makeText(
                    context,
                    context.getString(R.string.reset_pass_success_message),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
                viewModel.resetPass()

            }


            is NetworkState.Loading -> {

                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp), color = Color.White

                )

            }

            is NetworkState.Error -> {


                Toast.makeText(
                    context,
                    context.getString(androidx.compose.ui.R.string.default_error_message),
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.resetPass()

            }


            null -> {}

        }

    }

}