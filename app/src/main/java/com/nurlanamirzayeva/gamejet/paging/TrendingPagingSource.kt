package com.nurlanamirzayeva.gamejet.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository

class TrendingPagingSource(private val mainPageRepository: MainPageRepository) :
    PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {

        return try {
            val currentPage = params.key ?: 1
            val movies = mainPageRepository.getTrendingNow(page = currentPage)

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