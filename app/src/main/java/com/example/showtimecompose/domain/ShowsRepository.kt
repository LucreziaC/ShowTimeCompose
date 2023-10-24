package com.example.showtimecompose.domain

import com.example.showtimecompose.domain.states.LoadShowDetailResult
import com.example.showtimecompose.domain.states.LoadShowsResult


/**
 * Repository abstraction
 */
interface ShowsRepository {
    suspend fun loadShows(): LoadShowsResult
    suspend fun loadShowDetail(name: String): LoadShowDetailResult

}
