package com.example.showtimecompose.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ShowItemModel (
    val id:Int,
    val name: String? = null,
    val image: Image?=null,
    val genres: List<String>?=null,
    val summary: String?=null,
    val premiered: String?=null,
    var preferred:Boolean = false
    )
