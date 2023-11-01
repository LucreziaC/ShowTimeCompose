package com.example.showtimecompose.network.api

import com.example.showtimecompose.network.models.SearchList
import com.example.showtimecompose.network.models.ShowsList
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val service: ApiService) : ApiHelper {

    override suspend fun getShowsList(pageNum:Int): ShowsList = service.getShowsList(pageNum)
    override suspend fun getSearchList(query: String): SearchList = service.getSearchList(query)


}
