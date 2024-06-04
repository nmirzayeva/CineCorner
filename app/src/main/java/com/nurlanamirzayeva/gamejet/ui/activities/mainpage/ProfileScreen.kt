package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.view.login.HomeActivity
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun ProfileScreen(navController: NavHostController, mainPageViewModel: MainPageViewModel) {

    val accountSettingItems = listOf("Dark Mode", "History", "Edit Profile")
    val helpAndSupportAccounts =
        listOf("Privacy policy", "FAQ & Help", "Terms & Conditions")

    val profileItemState = mainPageViewModel.profileInfo.collectAsState()
    val profileImageUploadState = mainPageViewModel.profileImageUploadState.collectAsState()


    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        mainPageViewModel.fetchUserData()

    }
    val auth = FirebaseAuth.getInstance()
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                mainPageViewModel.uploadProfileImage(uri)
            }
        }
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dark_grey)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Profile",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {


            when (val response=profileItemState.value) {

                is NetworkState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }
                is NetworkState.Success -> {

                        AsyncImage(
                            model = response.data.profileImage,
                            contentDescription = "Profile",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .padding(top = 18.dp, bottom = 14.dp)
                                .padding()
                                .size(120.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center)
                        )

                }

                is NetworkState.Error -> {
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }

                null -> {}

            }




            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit",
                tint = sky_blue,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }

            )
        }


        when (val response = profileItemState.value) {

            is NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(50.dp)
                )
            }

            is NetworkState.Success -> {
                Text(
                    text = response.data.profileName ?: "Unknown",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 12.dp)
                )
                Text(
                    text = response.data.profileEmail ?: "Unknown",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp)
                )


            }

            is NetworkState.Error -> {
                errorMessage =
                    response.errorMessage ?: context.getString(R.string.error_message)

                Toast.makeText(
                    context,
                    errorMessage, Toast.LENGTH_SHORT
                ).show()

            }

            null -> {}

        }


        Text(
            "Account Settings",
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 14.dp, top = 40.dp)
        )

        accountSettingItems.forEachIndexed { index, accountSettingItems ->
            when (index) {
                0 -> ProfileItem(
                    text = accountSettingItems,
                    onClick = { navController.navigate(Screens.DarkMode) })

                1 -> ProfileItem(
                    text = accountSettingItems,
                    onClick = { navController.navigate(Screens.History) })

                2 -> ProfileItem(
                    text = accountSettingItems,
                    onClick = { navController.navigate(Screens.EditProfile) })


            }
        }




        Text(
            "Help and Support",
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 14.dp, top = 24.dp)
        )

        helpAndSupportAccounts.forEachIndexed { index,helpAndSupportAccountsItem  ->
            when (index) {
                0 -> ProfileItem(
                    text = helpAndSupportAccountsItem,
                    onClick = { navController.navigate(Screens.EditPassword) })

                1 -> ProfileItem(
                    text = helpAndSupportAccountsItem,
                    onClick = { navController.navigate(Screens.History) })

                2 -> ProfileItem(
                    text =helpAndSupportAccountsItem,
                    onClick = { navController.navigate(Screens.EditProfile) })


            }
        }
        Text(
            "Log Out",
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 14.dp, top = 10.dp, bottom = 80.dp)
                .clickable {
                    auth.signOut()
                    val intent = Intent(context, HomeActivity::class.java).apply {
                        putExtra("SKIP_SPLASH", true)
                    }
                    context.startActivity(intent)
                    (context as? Activity)?.finish()

                }
        )



        LaunchedEffect(profileImageUploadState.value) {
            when (profileImageUploadState.value) {
                is NetworkState.Loading -> {
                    // Handle loading state
                }
                is NetworkState.Success -> {
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                is NetworkState.Error -> {
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
                null -> {}
            }
        }


    }


}

@Composable
fun ProfileItem(text: String, onClick: (() -> Unit) = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
            .background(color = dark_grey)
            .height(36.dp)
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)


            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )

        }


    }
}


