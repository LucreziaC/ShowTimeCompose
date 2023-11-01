package com.example.showtimecompose.network.api

import com.example.showtimecompose.network.models.SearchList
import com.example.showtimecompose.network.models.ShowsList
import com.example.showtimecompose.network.models.ShowsListItem


interface ApiHelper {

    suspend fun getShowsList(pageNum:Int): ShowsList
    suspend fun getSearchList(query:String): SearchList
    suspend fun getShowDetails(showId:String): ShowsListItem

}
