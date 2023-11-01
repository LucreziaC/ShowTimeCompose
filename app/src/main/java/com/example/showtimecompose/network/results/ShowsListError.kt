package com.example.showtimecompose.network.results

sealed class ShowsListError {
    data object NoShowsFound : ShowsListError()
    data class GenericError(val message: String?): ShowsListError()
}
