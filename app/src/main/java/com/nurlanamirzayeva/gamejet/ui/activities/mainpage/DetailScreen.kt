package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.google.android.play.integrity.internal.i
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.ui.components.TextSwitch
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun DetailScreen(mainPageViewModel: MainPageViewModel) {
    val detailItemState = mainPageViewModel.detailPageResponse.collectAsState()
    val videoItemState = mainPageViewModel.videoListResponse.collectAsState()
    val creditItemState =mainPageViewModel.creditListResponse.collectAsState()

    LaunchedEffect(key1 = mainPageViewModel.movieId.intValue) {
        mainPageViewModel.getDetails()
        mainPageViewModel.getVideos()
        mainPageViewModel.getCredits()
    }

    detailItemState.value?.let {
        val tabItems = remember { listOf("Actors", "Similar Movies") }
        val videoItems = videoItemState.value?.results ?: emptyList()

        val teaserVideo = videoItems.find { video ->
            video?.type == "Trailer"
        }

        val teaserVideoKey=teaserVideo?.key?:""

        var selectedIndex by remember {
            mutableIntStateOf(0)
        }

        val (hours, remainingMinutes) = convertToHours(it.runtime!!)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = dark_grey)
                .verticalScroll(rememberScrollState())
        ) {

            Box()
            {
                if(teaserVideoKey.isNotEmpty()) {
                    YouTubePlayer(
                        youtubeVideoId = teaserVideoKey,
                        lifeCycleOwner = LocalLifecycleOwner.current
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.title.toString(),
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .wrapContentWidth()
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
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                it.genres?.forEachIndexed { index, genre ->

                    when (index) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(36.dp)
                                    .background(color = black, shape = RoundedCornerShape(8.dp))
                                    .align(Alignment.CenterVertically)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {

                                Text(
                                    text = genre?.name.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }

                        1 -> {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(36.dp)
                                    .background(color = black, shape = RoundedCornerShape(8.dp))
                                    .align(Alignment.CenterVertically)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {

                                Text(
                                    text = genre?.name.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }

                    }

                }

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(36.dp)
                        .background(color = black, shape = RoundedCornerShape(8.dp))
                        .align(Alignment.CenterVertically)
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                ) {

                    Text(
                        text = "${hours}h ${remainingMinutes}m",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
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
                onSelection = { selectedIndex = it })

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 14.dp, top = 14.dp)
            ) {
                creditItemState.value?.cast?.let {creditItem->
                    items(items = creditItem) {credit->
                        ActorsItem(image = IMAGE_URL+ credit!!.profilePath )
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

fun convertToHours(minutes: Int): Pair<Int, Int> {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return Pair(hours, remainingMinutes)
}


@Composable
fun ActorsItem(image:String) {
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
            .height(350.dp)
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



