package com.example.showtimecompose.network

import ApiResult
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.showtimecompose.network.api.ApiHelper
import com.example.showtimecompose.network.models.SearchListItem
import com.example.showtimecompose.network.models.ShowsListItem
import com.example.showtimecompose.network.paging_source.ShowsPagingSource
import com.example.showtimecompose.network.results.ShowError
import com.example.showtimecompose.utils.PreferencesHelper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sharedPreferences: PreferencesHelper,
) : ShowsRepository {
    override fun getShowsList(): Flow<PagingData<ShowsListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1000,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ShowsPagingSource(apiHelper)
            }
        ).flow
        }

    override fun getSearchList(query:String): Flow<ApiResult<List<SearchListItem>>> {
        return flow{
            try {
                emit(ApiResult.Loading())
                val searchResult = apiHelper.getSearchList(query)
                if(searchResult.isEmpty()){
                    emit(ApiResult.Error(ShowError.NoShowsFound))
                }else{
                    emit(ApiResult.Success(data=searchResult))
                }
            }catch (e:Exception){
                emit(ApiResult.Error(ShowError.GenericError(e.message)))
            }
        }
    }

    override fun getShowDetails(showId: String): Flow<ApiResult<ShowsListItem>> {
        return flow{
            try {
                emit(ApiResult.Loading())
                val searchResult = apiHelper.getShowDetails(showId)
                emit(ApiResult.Success(searchResult))
            }catch (e:Exception){
                emit(ApiResult.Error(ShowError.GenericError(e.message)))
            }
        }
    }
}


