package com.nurlanamirzayeva.gamejet.view.mainpage

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.ui.activities.mainpage.Screens
import com.nurlanamirzayeva.gamejet.ui.components.SearchBar
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.navy_blue
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun MainPage(mainPageViewModel: MainPageViewModel, navController: NavHostController) {

    val moviesState = mainPageViewModel.movieListResponse.collectAsState()
    val trendingMoviesState = mainPageViewModel.trendingMovieResponse.collectAsState()
    val upcomingMoviesState = mainPageViewModel.upcomingMovieResponse.collectAsState()
    val profileItemState = mainPageViewModel.profileInfo.collectAsState()
    val removeHistoryState = mainPageViewModel.removeHistoryResponse.collectAsState()

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        mainPageViewModel.getMovieList()
        mainPageViewModel.getTrendingNow()
        mainPageViewModel.getUpcomingMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dark_grey)
            .padding(start = 14.dp, end = 14.dp, top = 16.dp, bottom = 70.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.pp),
                contentDescription = "Profile",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding()
                    .size(56.dp)
                    .clip(
                        CircleShape
                    )

            )

            when (val response = profileItemState.value) {

                is NetworkState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(50.dp)
                    )
                }

                is NetworkState.Success -> {
                    Text(
                        "Hello, ${response.data.profileName ?: "Unknown"}!",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 24.dp, top = 6.dp)
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

        }

        Icon(imageVector = Icons.Rounded.Search,
            contentDescription =null,
            tint=Color.White,
            modifier= Modifier
                .size(40.dp)
                .clickable {
                    navController.navigate(Screens.Search)

                } )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {
            Text(
                "Discover",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                "View All",
                fontSize = 20.sp,
                color = sky_blue,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.clickable { navController.navigate(Screens.ViewAllDiscover) })
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {

            moviesState.value?.results?.let {

                items(items = it) { movie ->

                    MovieItems(imageUrl = IMAGE_URL + movie.posterPath, onClick = {
                        Log.d("TAG", "MainPage:${movie.id} ")
                        movie.id?.let { movieId ->
                            mainPageViewModel.movieId.intValue = movieId


                        }
                        navController.navigate(Screens.Detail)
                    })

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
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "View All",
                color = sky_blue,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .clickable { navController.navigate(Screens.ViewAllTrending) }
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {

            trendingMoviesState.value?.results?.let {

                items(items = it) { movie ->

                    MovieItems(imageUrl = IMAGE_URL + movie?.posterPath, onClick = {
                        movie?.id?.let { movieId ->
                            mainPageViewModel.movieId.intValue = movieId

                        }
                        navController.navigate(route = Screens.Detail)

                    })

                }
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "Upcoming",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "View All",
                color = sky_blue,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(
                        Alignment.CenterVertically
                    )
                    .clickable { navController.navigate(Screens.ViewAllUpcoming) }
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {

            upcomingMoviesState.value?.results?.let { upcomingList ->

                items(items = upcomingList) { upcoming ->

                    MovieItems(imageUrl = IMAGE_URL + upcoming?.posterPath, onClick = {
                        upcoming?.id?.let { upcomingId ->
                            mainPageViewModel.movieId.intValue = upcomingId


                        }
                        navController.navigate(route = Screens.Detail)

                    })

                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

    }



    when (val response = removeHistoryState.value) {
        is NetworkState.Loading -> {

        }

        is NetworkState.Success -> {


            mainPageViewModel.resetRemoveHistoryState()


        }

        is NetworkState.Error -> {
            errorMessage =
                response.errorMessage ?: context.getString(R.string.error_message)

            Toast.makeText(
                context,
                errorMessage, Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
    }


}







@Composable
fun MovieItems(imageUrl: String, onClick: () -> Unit) {


    Box(
        modifier = Modifier
            .height(150.dp)
            .width(100.dp)
            .clickable { onClick() }
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


