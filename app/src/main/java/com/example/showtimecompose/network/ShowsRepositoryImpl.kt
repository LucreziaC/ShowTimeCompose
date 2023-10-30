package com.example.showtimecompose.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.showtimecompose.network.api.ApiHelper
import com.example.showtimecompose.network.models.ShowsListItem
import com.example.showtimecompose.network.paging_source.ShowsPagingSource
import com.example.showtimecompose.utils.PreferencesHelper

import kotlinx.coroutines.flow.Flow
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
    }


