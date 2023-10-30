package com.example.showtimecompose.repository.api

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val service: ApiService) : ApiHelper {

    override suspend fun getShowsList() = service.getShowsList()


}
