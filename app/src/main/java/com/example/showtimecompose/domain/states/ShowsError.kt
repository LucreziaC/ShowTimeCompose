package com.example.showtimecompose.domain.states

sealed class ShowsError {
    object NoShowsFound : ShowsError()
    data class GenericError(val message: String?): ShowsError()
}
