package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.model.DetailsResponse

import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.ui.components.TextSwitch
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun DetailScreen(mainPageViewModel: MainPageViewModel,navController: NavHostController) {
    val detailItemState = mainPageViewModel.detailPageResponse.collectAsState()
    val videoItemState = mainPageViewModel.videoListResponse.collectAsState()
    val creditItemState = mainPageViewModel.creditListResponse.collectAsState()
    val similarItemState=mainPageViewModel.similarListResponse.collectAsState()
    val favoriteFilm=mainPageViewModel.addFavoriteFilms.collectAsState()
    val checkFavoriteState=mainPageViewModel.checkFavoriteResponse.collectAsState()
    val removeFavoriteState=mainPageViewModel.removeFavoriteResponse.collectAsState()


    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    var isFavoriteFilm by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = mainPageViewModel.movieId.intValue) {
        mainPageViewModel.checkFavorite()
        mainPageViewModel.getDetails()
        mainPageViewModel.getVideos()
    }



    detailItemState.value?.let {
        val tabItems = remember { listOf("Actors", "Similar Movies") }

        var selectedIndex by remember {
            mutableIntStateOf(0)
        }

        LaunchedEffect(key1 = selectedIndex ){
            if(selectedIndex== 0 && creditItemState.value == null ){
                mainPageViewModel.getCredits()
            }
            else{
                mainPageViewModel.getSimilar()
            }

        }

        val (hours, remainingMinutes) = convertToHours(it.runtime!!)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = dark_grey)
                .verticalScroll(rememberScrollState())
        ) {


            when (val response=videoItemState.value) {
                is NetworkState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is NetworkState.Success -> {
                    val videoItem =
                        (videoItemState.value as NetworkState.Success<Videos>).data.results?.find { video ->
                            video?.type == "Trailer"
                        }?.key ?: ""
                    if (videoItem.isNotEmpty()) {
                        YouTubePlayer(
                            youtubeVideoId = videoItem,
                            lifeCycleOwner = LocalLifecycleOwner.current
                        )
                    }
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.title.toString(),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(230.dp)
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
                        .align(Alignment.CenterVertically)
                ) {

                    Text(
                        text = "IMDB: ${it.voteAverage.toString()}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp),modifier=Modifier.padding(start=10.dp,end=10.dp,top=10.dp)) {
                it.genres?.forEachIndexed { index, genre ->
                    when (index) {
                        0 -> {
                            item { GenresViewItem(genreName = genre?.name.toString())}
                        }


                        1 -> {
                           item{ GenresViewItem(genreName = genre?.name.toString())}
                        }

                    }

                }
                  item{GenresViewItem(genreName = "${hours}h ${remainingMinutes}m")}

                item{ Icon(imageVector = if(isFavoriteFilm) Icons.Rounded.Favorite  else Icons.Rounded.FavoriteBorder , contentDescription =null,tint= if(isFavoriteFilm) Color.Red else Color.White , modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        it.id?.let { movieId ->
                            if (!isFavoriteFilm) {
                                mainPageViewModel.addFavorite(
                                    FavoriteFilm(
                                        id = movieId,
                                        userId = mainPageViewModel.userId,
                                        title = it.title ?: "No Title",
                                        posterPath = it.posterPath,
                                        voteAverage = it.voteAverage as Double
                                    )
                                )
                            } else {
                                mainPageViewModel.removeFavorite()
                            }
                        }


                    }


                )}

            }

            Text(
                "Movie Story:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(start = 14.dp, top = 16.dp)
            )
            Text(
                text = it.overview.toString(),
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .wrapContentHeight()

            )
            TextSwitch(
                selectedIndex = selectedIndex,
                items = tabItems,
                onSelection = {
                    selectedIndex = it
                })

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 14.dp, top = 14.dp)
            ) {
                if (selectedIndex == 0){
                    creditItemState.value?.cast?.let { creditItem ->
                        items(items = creditItem) { credit ->
                            ActorsItem(image = IMAGE_URL + credit!!.profilePath)
                        }
                    }
            }
                else{
                    similarItemState.value?.results?.let{similarItem->
                        items(items = similarItem) { similar ->
                            ActorsItem(image = IMAGE_URL + similar!!.posterPath)
                        }



                    }

                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

    }
    when (val response =favoriteFilm.value) {
        is NetworkState.Loading -> {

        }

        is NetworkState.Success -> {

            isFavoriteFilm=true
            mainPageViewModel.resetAddFavoriteState()


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

    when (val response =checkFavoriteState.value) {
        is NetworkState.Loading -> {

        }

        is NetworkState.Success -> {

            isFavoriteFilm= response.data
            mainPageViewModel.resetFavorite()


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

    when (val response =removeFavoriteState.value) {
        is NetworkState.Loading -> {

        }

        is NetworkState.Success -> {

            isFavoriteFilm= false
            mainPageViewModel.resetRemoveFavoriteState()


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
fun convertToHours(minutes: Int): Pair<Int, Int> {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return Pair(hours, remainingMinutes)
}


@Composable
fun ActorsItem(image: String) {
    AsyncImage(
        model = image, contentDescription = "TabIndicatorImages", modifier = Modifier
            .height(130.dp)
            .width(80.dp)
            .clip(shape = RoundedCornerShape(8.dp)), contentScale = ContentScale.FillBounds
    )

}


@Composable
fun YouTubePlayer(youtubeVideoId: String, lifeCycleOwner: LifecycleOwner) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        factory = {

            YouTubePlayerView(context = it).apply {

                lifeCycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }

                })

            }
        })

}

@Composable
fun GenresViewItem(genreName:String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(36.dp)
            .background(color = black, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {

        Text(
            text = genreName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(
                Alignment.Center
            )
        )

    }

}



