package com.example.showtimecompose.views.home_screen

import ApiResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.showtimecompose.network.ShowsRepository
import com.example.showtimecompose.network.api.ApiService
import com.example.showtimecompose.network.models.ShowsListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.showtimecompose.network.models.toDomain
import kotlinx.coroutines.flow.map


@HiltViewModel
class ShowListViewModel @Inject constructor(private val repository: ShowsRepository
                                            ,
                                            private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _showList= MutableStateFlow<ApiResult<List<ShowsListItem>>>(ApiResult.Loading())
    val showList= _showList.asStateFlow()

    init {
       // getShows()
    }

    val showListState =
        repository.getShowsList().map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }.cachedIn(viewModelScope)
}
