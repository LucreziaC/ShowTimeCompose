package com.example.showtimecompose.network.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.showtimecompose.network.api.ApiHelper
import com.example.showtimecompose.network.models.ShowsListItem
import javax.inject.Inject

class ShowsPagingSource @Inject constructor(
    private val apiHelper: ApiHelper
) : PagingSource<Int, ShowsListItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>):  LoadResult<Int, ShowsListItem> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiHelper.getShowsList(page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowsListItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
