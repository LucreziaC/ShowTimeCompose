package com.example.showtimecompose.views.detail_screen

import ApiResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.showtimecompose.network.ShowsRepository
import com.example.showtimecompose.network.models.ShowItemModel
import com.example.showtimecompose.network.models.toDomain
import com.example.showtimecompose.network.results.ShowsListError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowDetailViewModel @Inject constructor(private val repository: ShowsRepository
                                            ,
                                            private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    }

