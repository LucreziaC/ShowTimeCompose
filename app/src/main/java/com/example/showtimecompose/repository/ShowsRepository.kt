package com.example.showtimecompose.repository

import ApiResult
import com.example.showtimecompose.repository.models.ShowItemModel
import kotlinx.coroutines.flow.Flow


/**
 * Repository abstraction
 */
interface ShowsRepository {
    suspend fun getShowsList():  Flow<ApiResult<List<ShowItemModel>>>

    //suspend fun loadShowDetail(name: String): Flow<DataState<List<Show>>>

}
