package com.example.showtimecompose.network.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val service: ApiService) : ApiHelper {

    override suspend fun getShowsList(pageNum:Int) = service.getShowsList(pageNum)


}
