package com.test_task.movie_test.data.models

data class RequestModel(
    val copyright: String,
    val has_more: Boolean,
    val num_results: Int,
    val results: List<MovieModel>,
    val status: String
)