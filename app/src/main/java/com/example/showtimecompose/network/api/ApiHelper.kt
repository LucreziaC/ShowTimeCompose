package com.example.showtimecompose.network.api

import com.example.showtimecompose.network.models.ShowsList


interface ApiHelper {

    suspend fun getShowsList(pageNum:Int): ShowsList

}
