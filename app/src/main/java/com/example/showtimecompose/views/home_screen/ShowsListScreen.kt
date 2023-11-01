package com.example.showtimecompose.views.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.showtimecompose.navigation.NavigationItem
import com.example.showtimecompose.ui.components.ErrorComponent
import com.example.showtimecompose.ui.components.LoadingComponent
import com.example.showtimecompose.ui.components.SearchBarComponent
import com.example.showtimecompose.ui.components.ShowCard
import com.google.gson.Gson

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowListScreen(
    viewModel: ShowListViewModel = hiltViewModel(),
    navController:NavController
) {
    val list = viewModel.showListState.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            com.example.showtimecompose.ui.components.TopAppBar(scrollBehavior)
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(
                paddingValues
            ),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            SearchBarComponent(viewModel, navController)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxSize()
                        .padding(
                            horizontal = 8.dp
                        ),
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalItemSpacing = 4.dp,

                    ) {
                    items(list.itemCount) { index ->
                        list[index].let { show ->
                            if (show != null) {
                                ShowCard(show = show, viewModel, navController)
                            }
                        }
                    }
                    list.apply {
                        item(
                            span = StaggeredGridItemSpan.FullLine
                        ) {
                            when {
                                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                    LoadingComponent()
                                }

                                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                    ErrorComponent()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




