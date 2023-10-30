package com.example.showtimecompose.repository.api

import com.example.showtimecompose.repository.models.ShowsList


interface ApiHelper {

    suspend fun getShowsList(): ShowsList

}
