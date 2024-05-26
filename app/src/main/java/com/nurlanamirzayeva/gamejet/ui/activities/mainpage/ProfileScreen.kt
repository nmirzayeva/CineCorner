package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun ProfileScreen(navController:NavHostController,mainPageViewModel: MainPageViewModel) {

    val accountSettingItems = listOf<String>("Dark Mode", "History", "Lists", "Diary")
    val helpAndSupportAccounts =
        listOf<String>("Privacy policy", "FAQ & Help", "Terms & Conditions")

    val profileItemState= mainPageViewModel.profileInfo.collectAsState()

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit){
        mainPageViewModel.fetchUserData()
    }

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
            Image(
                painter = painterResource(id = R.drawable.pp),
                contentDescription = "Profile",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(top = 18.dp, bottom = 14.dp)
                    .padding()
                    .size(120.dp)
                    .clip(
                        CircleShape
                    )
                    .align(Alignment.Center)
            )

            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit",
                tint = sky_blue,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.BottomCenter)
                    .clickable { navController.navigate(Screens.EditProfile) }
            )
        }


        when(val response=profileItemState.value){

            is NetworkState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(50.dp)
                )
            }

            is NetworkState.Success-> {
                Text(
                    text= response.data.profileName ?: "Unknown",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 12.dp)
                )
                Text(
                    text= response.data.profileEmail ?:"Unknown" ,
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

        accountSettingItems.forEach { accountSettingItem ->

            ProfileItem(text = accountSettingItem, onClick = {navController.navigate(Screens.History)})
        }

        Text(
            "Help and Support",
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 14.dp, top = 24.dp)
        )

        helpAndSupportAccounts.forEach { helpAndSupportAccountItem ->
            ProfileItem(text = helpAndSupportAccountItem)

        }
        Text(
            "Log Out",
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 14.dp, top = 10.dp, bottom = 80.dp )
        )

    }
}

@Composable
fun ProfileItem(text: String,onClick: (() -> Unit) = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
            .background(color = dark_grey)
            .height(36.dp)
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
                    .clickable { onClick() }

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


