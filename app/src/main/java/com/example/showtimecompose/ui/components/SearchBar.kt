package com.example.showtimecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.showtimecompose.views.home_screen.ShowListViewModel
import com.example.showtimecompose.views.home_screen.UIEvent
import com.example.showtimecompose.views.home_screen.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    viewModel: ShowListViewModel,
    navController:NavController
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = text,
        modifier = Modifier.padding(bottom=8.dp),
        onQueryChange = {
            text = it
            println("onQuery: $it")
            if (it.isNullOrEmpty()) active = false
            sendQueryToSearch(viewModel, it)
        },
        onSearch = { query ->
            active = false
            sendQueryToSearch(viewModel, query)
        },
        active = active,
        onActiveChange = {
            active = it
            text=""
            viewModel.onEvent(UIEvent.ResetState)
            println("ACTIVE: $active")
        },
        placeholder = { Text("type a show's name..." , color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (active)
                IconButton(onClick = { active = false; text = "" }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
        },

        ) {
        when (val state = viewModel.searchList.collectAsStateWithLifecycle().value) {
            is UIState.Loading -> {
                LoadingComponent()
            }

            is UIState.Content -> {
                Column(modifier = Modifier
                    .padding(
                        horizontal = 8.dp
                    ),) {
                    Text(text="Results: ", modifier=Modifier.padding(vertical=25.dp))
                    LazyVerticalStaggeredGrid(

                        columns = StaggeredGridCells.Fixed(2),
                        //verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalItemSpacing = 4.dp,

                        ) {
                        items(state.data) { showItemModel ->
                            ShowCard(show = showItemModel, viewModel, navController)

                        }

                    }
                }

            }

            is UIState.Error -> {
               ErrorComponent()
            }

            else -> {
               Box() {}
            }
        }
    }
}

fun sendQueryToSearch(viewModel: ShowListViewModel, query: String) {
    if (query.isEmpty() || query.isBlank()) {
        viewModel.onEvent(UIEvent.ResetState)
    } else {
        viewModel.onEvent(UIEvent.OnSearch(query = query))
    }
}
