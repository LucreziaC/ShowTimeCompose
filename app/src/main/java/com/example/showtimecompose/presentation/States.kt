package com.example.showtimecompose.presentation
import com.example.showtimecompose.domain.states.ShowsError

sealed class DataState<out T>{
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val error: ShowsError) : DataState<Nothing>()
    object Loading: DataState<Nothing>()
}
