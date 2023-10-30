package com.example.showtimecompose.repository.models

import kotlinx.serialization.Serializable

@Serializable
data class ShowItemModel (
    val name: String,
    val image: Image,
    val genres: List<String>,
)
