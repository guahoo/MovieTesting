package com.test_task.movie_test.data.repos

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.test_task.movie_test.data.models.MovieModel

interface MovieRepository {
     fun getAllMovies(): LiveData<PagingData<MovieModel>>
}