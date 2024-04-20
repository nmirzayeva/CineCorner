package com.nurlanamirzayeva.gamejet.Paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository

class MoviesPagingSource(
    private val mainPageRepository: MainPageRepository,

    ) : PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->

            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)


        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {

      val page= params.key?:1
        val response=mainPageRepository.getMovies(page=page)
        val data=response.body()?.results ?: emptyList()

        if(response.isSuccessful&&response.body()!=null){
            val nextKey= when {
                (params.loadSize * (page+1)) < response.body()!!.results?.size!!-> page+1
                else->null

            }

            return LoadResult.Page(

                data=data,
                prevKey = null,
                nextKey= nextKey
            )

        }

        return LoadResult.Page(
            data= listOf(),
            prevKey = null,
            nextKey = null

        )

    }


}