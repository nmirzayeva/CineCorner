package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.ui.components.SearchBar
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.view.mainpage.MovieItems
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun SearchMoviesScreen(mainPageViewModel: MainPageViewModel, navController: NavHostController) {
    val searchPageList = mainPageViewModel.searchResults.collectAsLazyPagingItems()


    LaunchedEffect(key1 = Unit ){
        mainPageViewModel.getSearchResults()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start=10.dp,end=10.dp,top=20.dp),

    ) {

              Column(modifier= Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SearchBar(hint = "Search", mainPageViewModel = mainPageViewModel)

            SearchResult(movies = searchPageList, onClick = {
                mainPageViewModel.movieId.intValue = it.id ?: 0
                navController.navigate(Screens.Detail)
            })

              }

    }
}

@Composable
fun SearchResult(movies: LazyPagingItems<ResultsItem>, onClick: (ResultsItem) -> Unit) {

    LazyVerticalGrid(columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            if (movie != null) {

                MovieItems(imageUrl = IMAGE_URL + movie.posterPath, onClick = { onClick(movie) })

            }

        }

    }
}
