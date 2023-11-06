package com.example.showtimecompose.ui.components

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

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
        when (val state = viewModel.showsState.collectAsStateWithLifecycle().value) {
            is UIState.Loading -> {
                LoadingComponent()
            }

            is UIState.Content -> {
                Column(modifier = Modifier
                    .padding(
                        horizontal = 8.dp
                    ),) {
                    Text(text="Results: ", modifier=Modifier.padding(vertical=25.dp))
                    ShowsList(state.data, viewModel, navController)
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
