package com.example.showtimecompose.repository

import ApiResult
import com.example.showtimecompose.repository.api.ApiHelper
import com.example.showtimecompose.repository.models.ShowItemModel
import com.example.showtimecompose.repository.models.ShowsList
import com.example.showtimecompose.repository.models.toDomain
import com.example.showtimecompose.utils.PreferencesHelper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sharedPreferences: PreferencesHelper,
) : ShowsRepository {
    override suspend fun getShowsList(): Flow<ApiResult<List<ShowItemModel>>> {
        return flow {
                try {
                    emit(ApiResult.Loading())
                    val showsList = apiHelper.getShowsList().map {
                        it.toDomain()
                    }
                    if(showsList.isNullOrEmpty()){
                        //TODO ERROR
                        emit(ApiResult.Error("NO items founs"))
                    }
                    else {
                        emit(ApiResult.Success(data = showsList))
                    }
                } catch (e: Exception) {
                    //TODO
                    emit(ApiResult.Error("errore"))
                }
            }
        }
    }


