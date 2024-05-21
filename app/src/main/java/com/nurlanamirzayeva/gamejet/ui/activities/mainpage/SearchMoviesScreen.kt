package com.nurlanamirzayeva.gamejet.ui.activities.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.ui.components.SearchBar
import com.nurlanamirzayeva.gamejet.ui.theme.dark_grey
import com.nurlanamirzayeva.gamejet.utils.IMAGE_URL
import com.nurlanamirzayeva.gamejet.view.mainpage.MovieItems
import com.nurlanamirzayeva.gamejet.viewmodel.MainPageViewModel


@Composable
fun SearchMoviesScreen(mainPageViewModel: MainPageViewModel, navController: NavHostController) {
    val searchPageList = mainPageViewModel.searchResults.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box {
              Column(modifier= Modifier.fillMaxWidth()) {
            SearchBar(hint = "Search", mainPageViewModel = mainPageViewModel)
            SearchResult(movies = searchPageList, onClick = {
                mainPageViewModel.movieId.intValue = it.id ?: 0
            })

              }
        }
    }
}

@Composable
private fun SearchResult(movies: LazyPagingItems<ResultsItem>, onClick: (ResultsItem) -> Unit) {

    LazyColumn {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            if (movie != null) {

                MovieItems(imageUrl = IMAGE_URL + movie.posterPath, onClick = { onClick(movie) })


            }


        }

    }


}
