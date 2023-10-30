package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class Externals(
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int
)
