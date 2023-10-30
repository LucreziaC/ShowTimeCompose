package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val nextepisode: Nextepisode,
    val previousepisode: Previousepisode,
    val self: Self
)
