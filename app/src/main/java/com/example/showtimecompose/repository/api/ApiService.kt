package com.example.showtimecompose.repository.api

import com.example.showtimecompose.repository.models.ShowsList

interface ApiService {
    suspend fun getShowsList(): ShowsList
}
