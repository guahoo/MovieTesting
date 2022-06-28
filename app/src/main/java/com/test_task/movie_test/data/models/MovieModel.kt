package com.test_task.movie_test.data.models

data class MovieModel(
    val byline: String,
    val critics_pick: Int,
    val date_updated: String,
    val display_title: String,
    val headline: String,
    val link: Link,
    val mpaa_rating: String,
    val multimedia: Multimedia,
    val opening_date: String,
    val publication_date: String,
    val summary_short: String
)