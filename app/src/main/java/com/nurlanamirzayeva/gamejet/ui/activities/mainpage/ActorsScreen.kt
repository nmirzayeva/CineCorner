package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.ui.components.TextSwitch
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.ui.theme.sky_blue
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.viewmodel.ActorViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun ActorsScreen(actorPageViewModel: ActorViewModel,mainPageViewModel:MainPageViewModel,navController:NavHostController) {

    val actorDetailState = actorPageViewModel.actorDetailResponse.collectAsState()
    val actorImageState = actorPageViewModel.actorImageResponse.collectAsState()
    val actorMovieState = actorPageViewModel.actorMoviesResponse.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val tabItems = remember { listOf("Photos", "Movies") }
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    var photosFetched by remember { mutableStateOf(false) }
    var moviesFetched by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = actorPageViewModel.personId.intValue) {
        actorPageViewModel.getActorDetails()
    }

    LaunchedEffect(key1 = selectedIndex) {
        if (selectedIndex == 0 && !photosFetched) {
           actorPageViewModel.getActorImages()
            photosFetched = true
        } else if (selectedIndex == 1 && !moviesFetched) {
           actorPageViewModel.getActorMovies()
            moviesFetched= true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf( dark_grey, dark_grey,dark_grey,Color(0xFF3F5F8D),),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .verticalScroll(rememberScrollState())
    ) {


            AsyncImage(
                model = IMAGE_URL + actorDetailState.value?.profilePath,
                contentDescription = null,
                modifier = Modifier
                    .height(270.dp)
                    .padding(16.dp)
            )


        Text(
            text = actorDetailState.value?.name ?: "Unknown",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Text(
            text = if (expanded) actorDetailState.value?.biography ?: "Unknown" else "${(actorDetailState.value?.biography ?: "Unknown").take(300)}...",
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clickable { expanded = !expanded }
        )

        if (!expanded && (actorDetailState.value?.biography ?: "Unknown").length > 300) {
            Text(
                text="View More",
                color = sky_blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(all = 16.dp)
            )
        } else if (expanded) {
            Text(text= "View Less",
                color = sky_blue,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable { expanded = false }
                    .padding(all = 16.dp)
            )
        }

        TextSwitch(
            selectedIndex = selectedIndex,
            items = tabItems,
            onSelection = {
                selectedIndex = it
            }
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 14.dp, top = 14.dp)
        ) {
            if (selectedIndex == 0) {
                actorImageState.value?.profiles?.let { profileItem ->
                    items(items = profileItem) { profile ->
                        SwitchItem(image = IMAGE_URL + profile?.filePath)
                    }
                }
            } else {
                actorMovieState.value?.cast?.let { movieItem ->
                    items(items = movieItem) { movie ->
                        ActorsItem(image = IMAGE_URL + movie?.posterPath, onClick ={
                            movie?.id.let {movieId->
                                mainPageViewModel.movieId.intValue= movieId ?:0
                            }
                            navController.navigate(Screens.Detail)

                        } )
                    }
                }
            }
        }

    }
}

@Composable
fun SwitchItem(image: String,onClick:()->Unit={}) {
    AsyncImage(
        model = image, contentDescription = "TabIndicatorImages", modifier = Modifier
            .height(130.dp)
            .width(80.dp)
            .clickable { onClick() }
            .clip(shape = RoundedCornerShape(8.dp)), contentScale = ContentScale.FillBounds

    )
}

