package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String,
    val original: String
)
