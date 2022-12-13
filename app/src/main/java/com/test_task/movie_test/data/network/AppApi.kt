package com.test_task.movie_test.data.network

import com.test_task.movie_test.data.models.RequestModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {
    @GET("svc/movies/v2/reviews/all.json")
    suspend fun getAllMovies(
        @Query("type") type: String = "all",
        @Query("offset") offset: Int
    ): Response<RequestModel>
}