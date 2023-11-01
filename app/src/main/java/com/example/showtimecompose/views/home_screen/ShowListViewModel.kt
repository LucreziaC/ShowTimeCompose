package com.example.showtimecompose.views.home_screen

import ApiResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.showtimecompose.network.ShowsRepository
import com.example.showtimecompose.network.models.ShowItemModel
import com.example.showtimecompose.network.models.toDomain
import com.example.showtimecompose.network.results.ShowError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowListViewModel @Inject constructor(private val repository: ShowsRepository
                                            ,
                                            private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _searchList= MutableStateFlow<UIState>(UIState.Default)
    val searchList= _searchList.asStateFlow()

    init {
       // getShows()
    }

    fun onEvent(event: UIEvent){
        when(event){
            is UIEvent.OnSearch -> searchShow(event.query)
            UIEvent.ResetState -> _searchList.value=UIState.Default
        }
    }

      fun searchShow(query: String) {
        viewModelScope.launch {
            repository.getSearchList(query).flowOn(defaultDispatcher).collect() {result->
                when(result){
                    is ApiResult.Loading ->{_searchList.value=UIState.Loading}
                    is ApiResult.Error ->{_searchList.value=UIState.Error(result.error ?: ShowError.GenericError("A problem has occured. Try again later"))}
                    else ->{
                        val data = result.data?.map{searchListItem->
                            searchListItem.show.toDomain()
                        }
                        _searchList.value=UIState.Content(data ?: emptyList())
                    }
                }
            }
        }
    }

    val showListState =
        repository.getShowsList().map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }.cachedIn(viewModelScope)
}

sealed class UIEvent{
    data class OnSearch(val query:String): UIEvent()
    data object ResetState : UIEvent()
}

sealed class UIState{
    data class Content(val data: List<ShowItemModel>):UIState()
    data class Error(val error:ShowError):UIState()
    data object  Loading: UIState()
    data object  Default: UIState()

}
