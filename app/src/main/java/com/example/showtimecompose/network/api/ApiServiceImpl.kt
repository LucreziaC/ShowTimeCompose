package com.example.showtimecompose.network.api

import com.example.showtimecompose.network.models.ShowsList
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiServiceImpl(private val client: HttpClient): ApiService {
    override suspend fun getShowsList(pageNum:Int): ShowsList {
        val response:ShowsList= client.get{
            url {
                protocol = URLProtocol.HTTPS
                host= APIUrls.BASE_URL
                path(APIUrls.SHOWS_LIST)
                encodedParameters.append("page", pageNum.toString())
            }
        }.body()
        return response
    }

}
