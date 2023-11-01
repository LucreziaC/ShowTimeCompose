package com.example.showtimecompose.network

import ApiResult
import androidx.paging.PagingData
import com.example.showtimecompose.network.models.SearchListItem
import com.example.showtimecompose.network.models.ShowsListItem
import kotlinx.coroutines.flow.Flow


/**
 * Repository abstraction
 */
interface ShowsRepository {
     fun getShowsList(): Flow<PagingData<ShowsListItem>>
     fun getSearchList(query:String): Flow<ApiResult<List<SearchListItem>>>
     fun getShowDetails(showId:String): Flow<ApiResult<ShowsListItem>>
    //suspend fun loadShowDetail(name: String): Flow<DataState<List<Show>>>

}
