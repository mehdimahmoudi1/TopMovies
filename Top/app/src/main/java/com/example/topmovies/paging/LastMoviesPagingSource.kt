package com.example.topmovies.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.topmovies.models.lastmovies.ResponseLastMovies
import com.example.topmovies.repositories.topmovies.HomeRepository
import javax.inject.Inject

class LastMoviesPagingSource @Inject constructor(
    private val repository: HomeRepository
) : PagingSource<Int, ResponseLastMovies.Data>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseLastMovies.Data>): Int? {
        return null
    }
//load
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseLastMovies.Data> {
        return try {
            //get current page
            val currentPage = params.key ?: 1
            //get list
            val response = repository.getLastMovies(currentPage)
            //set data list
            val data = response.body()?.data ?: emptyList()
            //set Data response
            val responseData = mutableListOf<ResponseLastMovies.Data>()
            responseData.addAll(data)
            //handle load result
            LoadResult.Page(
                responseData, if (currentPage == 1) null else -1, currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}