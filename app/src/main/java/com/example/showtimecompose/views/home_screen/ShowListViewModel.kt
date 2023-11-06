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
import com.example.showtimecompose.utils.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


@HiltViewModel
class ShowListViewModel @Inject constructor(
    private val repository: ShowsRepository,
    private val defaultDispatcher: CoroutineDispatcher,
    private val preferences: PreferencesHelper
) : ViewModel() {
    private val _showsState = MutableStateFlow<UIState>(UIState.Default)
    val showsState = _showsState.asStateFlow()
    val favourites = preferences.getStringPreference(preferences.preferencesKey)
    var favouriteslList: MutableList<ShowItemModel>? = null




    init {
         //getFavourites()
    }

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnSearch -> searchShow(event.query)
            UIEvent.ResetState -> _showsState.value = UIState.Default
            is UIEvent.AddToFavourite -> {
                addToFavorite(event)
            }

            is UIEvent.RemoveFromFavourite -> {
                removeFromFavorite(event)
            }

            is UIEvent.GetFavourite -> {
                getFavourites()
            }
        }
    }

    private fun getFavourites(){
        _showsState.value= UIState.Loading
        val favourites = preferences.getStringPreference(preferences.preferencesKey)
        if(favourites.isNullOrEmpty()){
            _showsState.value= UIState.Error(ShowError.NoShowsFound)
        }else{
            _showsState.value = UIState.Content(Json.decodeFromString(favourites))

        }
    }

    private fun removeFromFavorite(event: UIEvent.RemoveFromFavourite) {
        favouriteslList = Json.decodeFromString(favourites!!)
        favouriteslList?.remove(event.show)
        val jsonList = Json.encodeToString(favouriteslList)
        preferences.setStringPreference(preferences.preferencesKey, jsonList)
    }

    private fun addToFavorite(event: UIEvent.AddToFavourite) {
        val favourites = preferences.getStringPreference(preferences.preferencesKey)
        if (favourites.isNullOrEmpty()) {
            favouriteslList = mutableListOf(event.show)

        } else {
            favouriteslList = Json.decodeFromString(favourites)
            favouriteslList?.add(event.show.copy(preferred = true))
        }
        val jsonList = Json.encodeToString(favouriteslList)
        preferences.setStringPreference(preferences.preferencesKey, jsonList)
    }

    fun searchShow(query: String) {
        viewModelScope.launch {
            repository.getSearchList(query).flowOn(defaultDispatcher).collect() { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _showsState.value = UIState.Loading
                    }

                    is ApiResult.Error -> {
                        _showsState.value = UIState.Error(
                            result.error ?: ShowError.GenericError("A problem has occured. Try again later")
                        )
                    }

                    else -> {
                        val data = result.data?.map { searchListItem ->
                            searchListItem.show.toDomain()
                        }
                        _showsState.value = UIState.Content(data ?: emptyList())
                    }
                }
            }
        }
    }


    val showListState =
    repository.getShowsList().map { pagingData ->
            pagingData.map {item->
               val domainItem= item.toDomain()
                if(!favourites.isNullOrEmpty()) {
                    favouriteslList = Json.decodeFromString(favourites)
                    val isPreferred = favouriteslList?.find{
                        it.id==domainItem.id
                    }
                    domainItem.copy(preferred = isPreferred!=null)
                    }
                return@map domainItem
            }
        }.cachedIn(viewModelScope)
}

sealed class UIEvent {
    data class OnSearch(val query: String) : UIEvent()
    data object ResetState : UIEvent()
    data class AddToFavourite(val show: ShowItemModel) : UIEvent()
    data class RemoveFromFavourite(val show: ShowItemModel) : UIEvent()
    data object GetFavourite : UIEvent()

}

sealed class UIState {
    data class Content(val data: List<ShowItemModel>) : UIState()
    data class Error(val error: ShowError) : UIState()
    data object Loading : UIState()
    data object Default : UIState()

}
