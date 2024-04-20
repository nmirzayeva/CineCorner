package com.nurlanamirzayeva.gamejet.view.mainpage

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.Screens
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.navy_blue
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun MainPage(mainPageViewModel: MainPageViewModel, navController: NavHostController) {

    val moviesState = mainPageViewModel.movieListResponse.collectAsState()

    LaunchedEffect(key1 = Unit) {


        mainPageViewModel.getMovieList()
    }


    Box(
        modifier = Modifier

            .fillMaxSize()
            .background(color = dark_grey)
            .padding(start = 14.dp, end = 14.dp, top = 16.dp)
    ) {


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {


            Text("Store", fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    "Discover",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    "View All",
                    fontSize = 20.sp,
                    color = sky_blue,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.clickable { navController.navigate(Screens.ViewAllDiscover) })
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),

                ) {

                moviesState.value?.results?.let {

                    items(items = it) { movie ->

                        SpecialOfferItems(imageUrl = IMAGE_URL + movie.posterPath)

                    }
                }


            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Text(
                    text = "Trending Now",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "View All",
                    color = sky_blue,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier.align(
                        Alignment.CenterVertically
                    )
                )
            }


        }


    }


}


@Composable
fun SpecialOfferItems(imageUrl: String) {


    Box(
        modifier = Modifier
            .height(150.dp)
            .width(100.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(shape = RoundedCornerShape(8.dp), color = navy_blue)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier

                .height(150.dp)
                .width(100.dp)
                .background(shape = RoundedCornerShape(8.dp), color = Color.Transparent)

        )

    }


}
