package com.nurlanamirzayeva.gamejet.paging



import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nurlanamirzayeva.gamejet.model.TrendingItem
import com.nurlanamirzayeva.gamejet.model.UpcomingItem
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import javax.inject.Inject

class UpcomingPagingSource @Inject constructor  (
    private val mainPageRepository: MainPageRepository

) : PagingSource<Int, UpcomingItem>() {
    override fun getRefreshKey(state: PagingState<Int, UpcomingItem>): Int? {
        Log.d("TAG", "getRefreshKey:${state.anchorPosition} ")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UpcomingItem> {

        return try {

            val currentPage = params.key ?: 1
            val movies = mainPageRepository.getUpcomingMovies(page = currentPage)

            LoadResult.Page(
                data = movies.body()?.results?.filterNotNull().orEmpty() ,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.body()?.results!!.isEmpty()) null else movies.body()?.page!! + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }


    }


}