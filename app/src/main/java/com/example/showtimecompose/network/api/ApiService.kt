package com.example.showtimecompose.network.api

import com.example.showtimecompose.network.models.SearchList
import com.example.showtimecompose.network.models.ShowsList

interface ApiService {
    suspend fun getShowsList(pageNum:Int): ShowsList
    suspend fun getSearchList(query:String): SearchList
}
