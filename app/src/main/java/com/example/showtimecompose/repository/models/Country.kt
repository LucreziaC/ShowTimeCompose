package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String,
    val name: String,
    val timezone: String
)
