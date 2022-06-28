package com.test_task.movie_test.ui.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test_task.movie_test.data.models.MovieModel

import com.test_task.movie_test.data.repos.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val needShowErrorScreen = MutableLiveData(false)
    val isRefreshing = MutableLiveData<Boolean>()

    fun getMovieList(): LiveData<PagingData<MovieModel>> {
        return movieRepository.getAllMovies().cachedIn(viewModelScope)
    }



}

