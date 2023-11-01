package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String,
    val name: String,
    val timezone: String
)
