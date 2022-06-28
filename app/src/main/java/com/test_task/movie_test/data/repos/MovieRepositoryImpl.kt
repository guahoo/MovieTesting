package com.test_task.movie_test.data.repos


import androidx.lifecycle.LiveData
import androidx.paging.*
import com.test_task.movie_test.data.models.MovieModel
import com.test_task.movie_test.data.source.MovieDataSource
import com.test_task.movie_test.data.network.AppApi
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val appApi: AppApi): MovieRepository {

    override fun getAllMovies(): LiveData<PagingData<MovieModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
              enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MovieDataSource(appApi)
            }
        ).liveData
    }


}