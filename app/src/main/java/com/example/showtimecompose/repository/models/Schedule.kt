package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>,
    val time: String
)
