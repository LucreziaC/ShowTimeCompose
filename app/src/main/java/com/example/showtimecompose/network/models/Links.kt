package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val nextepisode: Nextepisode,
    val previousepisode: Previousepisode,
    val self: Self
)
