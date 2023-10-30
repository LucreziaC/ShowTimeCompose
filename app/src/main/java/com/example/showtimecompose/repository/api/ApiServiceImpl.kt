package com.example.showtimecompose.repository.api

import com.example.showtimecompose.repository.models.ShowsList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ApiServiceImpl(private val client: HttpClient): ApiService {
    override suspend fun getShowsList(): ShowsList {
        return client.get{
            url(APIUrls.SHOWS_LIST)
        }.body()
    }

}
