package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Externals(
    val imdb: String,
    val thetvdb: Int,
    val tvrage: Int
)
