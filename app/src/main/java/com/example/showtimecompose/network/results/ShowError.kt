package com.example.showtimecompose.network.results

sealed class ShowError {
    data object NoShowsFound : ShowError()
    data class GenericError(val message: String?): ShowError()
}
