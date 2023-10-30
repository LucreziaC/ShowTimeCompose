package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ShowItemModel (
    val name: String?,
    val image: Image?,
    val genres: List<String>?,
)
