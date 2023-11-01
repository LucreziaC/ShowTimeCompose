package com.example.showtimecompose.views.detail_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.showtimecompose.ui.components.ErrorComponent
import com.example.showtimecompose.ui.components.LoadingComponent
import com.example.showtimecompose.ui.components.ShowCard
import com.example.showtimecompose.ui.components.TopAppBar
import com.example.showtimecompose.views.home_screen.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreen(showId: String) {
    val viewModel: ShowDetailViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    viewModel.onEvent(UIDetailEvent.OnSearch(id = showId))
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(scrollBehavior)
        },

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(
                    paddingValues
                ),
        ) {
            when (val state = viewModel.showDetailState.collectAsStateWithLifecycle().value) {
                is UIDetailState.Loading -> {
                    LoadingComponent()
                }

                is UIDetailState.Content -> {

                    Text(text = "Results: ", modifier = Modifier.padding(vertical = 25.dp))

                }


                is UIDetailState.Error -> {
                    ErrorComponent()
                }

                else -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
