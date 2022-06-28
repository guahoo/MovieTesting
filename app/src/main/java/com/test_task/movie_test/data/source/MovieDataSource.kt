package com.test_task.movie_test.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test_task.movie_test.data.models.MovieModel
import com.test_task.movie_test.data.network.AppApi

class MovieDataSource (private val apiService: AppApi): PagingSource<Int, MovieModel>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
            return try {
                val position = params.key ?: 20
                val response = apiService.getAllMovies(offset = position)

                LoadResult.Page(data = response.body()!!.results, prevKey = if (position == 20) null else position - 20,
                    nextKey = position + 20)

            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        }

        override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
            }
        }

    }
