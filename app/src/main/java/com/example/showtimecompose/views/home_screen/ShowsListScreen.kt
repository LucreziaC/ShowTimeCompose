package com.example.showtimecompose.views.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.showtimecompose.ui.components.ErrorComponent
import com.example.showtimecompose.ui.components.LoadingComponent
import com.example.showtimecompose.ui.components.ShowCard

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowListScreen(
    viewModel: ShowListViewModel = hiltViewModel()
){
    val list = viewModel.showListState.collectAsLazyPagingItems()
    println(list)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            com.example.showtimecompose.ui.components.TopAppBar(scrollBehavior)
        },

    ) {paddingValues->
        Column(
            modifier= Modifier.fillMaxSize().padding(
                paddingValues
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if(list == null) return@Column
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(
                       horizontal =  16.dp
                    ),
                columns = StaggeredGridCells.Fixed(2),
                //verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp,

                ) {
                items(list.itemCount) { index ->
                    list[index].let { show ->
                        //val id = show?.id
                        val name = show?.name ?: ""
                        ShowCard(image=show?.image?.original)
                    }
                }
                list.apply {
                    item(
                        span= StaggeredGridItemSpan.FullLine
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

    }}


