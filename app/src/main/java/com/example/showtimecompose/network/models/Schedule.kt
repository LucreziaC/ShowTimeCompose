package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>,
    val time: String
)
