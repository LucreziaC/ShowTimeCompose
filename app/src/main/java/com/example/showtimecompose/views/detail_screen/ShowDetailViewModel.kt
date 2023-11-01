package com.example.showtimecompose.views.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showtimecompose.network.ShowsRepository
import com.example.showtimecompose.network.models.ShowItemModel
import com.example.showtimecompose.network.models.toDomain
import com.example.showtimecompose.network.results.ShowError
import com.example.showtimecompose.views.home_screen.UIEvent
import com.example.showtimecompose.views.home_screen.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val repository: ShowsRepository,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _showDetailState = MutableStateFlow<UIDetailState>(UIDetailState.Default)
    val showDetailState = _showDetailState.asStateFlow()

    fun onEvent(event: UIDetailEvent) {
        when (event) {
            is UIDetailEvent.OnSearch -> {getShowDetails(event.id)}
            UIDetailEvent.ResetState -> TODO()
        }
    }

    fun getShowDetails(id: String) {
        viewModelScope.launch {
            repository.getShowDetails(id).flowOn(defaultDispatcher).collect() {result->
                when(result){
                    is ApiResult.Loading ->{_showDetailState.value=UIDetailState.Loading}
                    is ApiResult.Error ->{_showDetailState.value=UIDetailState.Error(result.error ?: ShowError.GenericError("A problem has occured. Try again later"))}
                    else ->{
                        val data = result.data?.toDomain()
                        _showDetailState.value=UIDetailState.Content(data?:ShowItemModel(-1, null, null, null))
                    }
                }
            }
        }
    }

}

sealed class UIDetailState {
    data class Content(val data: ShowItemModel) : UIDetailState()
    data class Error(val error: ShowError) : UIDetailState()
    data object Loading : UIDetailState()
    data object Default : UIDetailState()

}

sealed class UIDetailEvent {
    data class OnSearch(val id: String) : UIDetailEvent()
    data object ResetState : UIDetailEvent()
}

