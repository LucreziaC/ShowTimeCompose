package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class WebChannel(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String
)
