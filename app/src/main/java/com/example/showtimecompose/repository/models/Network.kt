package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val country: Country,
    val id: Int,
    val name: String,
    val officialSite: String
)
