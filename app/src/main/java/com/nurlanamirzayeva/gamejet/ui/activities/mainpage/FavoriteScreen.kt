package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.modifier.ModifierLocalMap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.ui.theme.black
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel
import com.nurlanamirzayeva.gamejet.viewmodel.SettingsViewModel
import org.w3c.dom.Text


@Composable
fun FavoriteScreen(mainPageViewModel: MainPageViewModel,navController: NavHostController) {

    val getFavoritesFilmsState = mainPageViewModel.getFavoriteResponse.collectAsState()
    val removeFavoriteState=mainPageViewModel.removeFavoriteResponse.collectAsState()
    val favoriteFilmLocal=mainPageViewModel.favoriteFilms.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(mainPageViewModel.movieId.intValue) {

        mainPageViewModel.getFavoriteFilms()
        mainPageViewModel.getFavoriteLocal()

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = dark_grey)
            .padding(start = 10.dp, end = 10.dp, top = 16.dp)
    ) {


            getFavoritesFilmsState.value.let { it ->

                when (val response = it) {

                    is NetworkState.Loading -> {
                       CircularProgressIndicator(modifier=Modifier.align(Alignment.Center),color=Color.White)
                    }

                    is NetworkState.Success -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
                        items(response.data) { favoriteFilm ->
                            FavoriteItem(item = favoriteFilm, onClick = {
                                mainPageViewModel.movieId.intValue = favoriteFilm.id ?: 0
                                navController.navigate(Screens.Detail)
                            }, onRemoveClick = {
                                mainPageViewModel.removeFavorite(favoriteFilm.id)
                            })
                        }
                    }
                    }

                    is NetworkState.Error -> {

                    }

                    else -> {
                        favoriteFilmLocal.value.let {favoriteFilmLocal->

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
                                items(favoriteFilmLocal) { favoriteFilm ->
                                    FavoriteItem(item = favoriteFilm)
                                }
                            }

                        }
                    }
                }


            }


        when (removeFavoriteState.value) {

            is NetworkState.Success -> {

                mainPageViewModel.getFavoriteFilms()
                mainPageViewModel.resetRemoveFavoriteState()

            }

            else -> {}
        }



    }
}


@Composable
fun FavoriteItem(item: FavoriteFilm,onClick:()->Unit={},onRemoveClick:()->Unit={}) {

    Box(
        modifier = Modifier
            .background(color = black, shape = RoundedCornerShape(8.dp))
            .height(170.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {

        AsyncImage(
            model = IMAGE_URL + item.posterPath, contentDescription = null, modifier = Modifier
                .padding(all = 8.dp)
                .width(110.dp)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(10.dp)), contentScale = ContentScale.FillBounds
        )

        Text(
            text = item.title ?: "Unknown",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(start = 56.dp, top = 14.dp)
                .width(170.dp)
        )

        Icon(
            imageVector = Icons.Rounded.Delete, contentDescription = null, modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .size(44.dp)
                .padding(top = 10.dp, end = 10.dp)
                .clickable { onRemoveClick() },
            tint = Color.White


        )

        Box(
            modifier = Modifier
                .padding(end = 10.dp, bottom = 10.dp)
                .width(100.dp)
                .height(30.dp)
                .background(color = Color.Yellow, shape = RoundedCornerShape(8.dp))
                .align(Alignment.BottomEnd)


        ) {

            Text(
                text = item.voteAverage.toString(),
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





