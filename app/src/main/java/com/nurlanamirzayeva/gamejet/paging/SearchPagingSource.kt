package com.nurlanamirzayeva.gamejet.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import javax.inject.Inject

class SearchPagingSource @Inject constructor  (
    private val mainPageRepository: MainPageRepository,
    private val searchTerm:String

) : PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        Log.d("TAG", "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {

        return try {

            val currentPage = params.key ?: 1
            val movies = mainPageRepository.getSearchMovies(page=currentPage, query = searchTerm )

            LoadResult.Page(
                data = movies.body()?.results!!,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.body()?.results!!.isEmpty()) null else movies.body()?.page!! + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }


    }


}