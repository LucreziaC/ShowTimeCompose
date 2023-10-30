package com.example.showtimecompose.views.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.showtimecompose.ui.components.ShowCard
import okhttp3.internal.assertThreadHoldsLock

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowListScreen(
    viewModel: ShowListViewModel = hiltViewModel()
){
    val list = viewModel.showListState.collectAsLazyPagingItems()
    println(list)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if(list == null) return@Surface
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp
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
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {

                                    CircularProgressIndicator(color = Color.Black)
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp),
                                        text = "Loading..."
                                    )
                                }
                            }
                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                ConstraintLayout(
                                ) {
                                    val (
                                        buttonText,
                                    ) = createRefs()
                                    TextButton(
                                        modifier = Modifier
                                            .constrainAs(buttonText){
                                                start.linkTo(parent.start)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                end.linkTo(parent.end)
                                            },
                                        border = BorderStroke(
                                            1.dp,
                                            Color.Blue,
                                        ),
                                        onClick = {},
                                    ){
                                        Text(text = "errore")
                                    }
                                }
                            }
                            else -> {
                                ConstraintLayout(
                                ) {
                                    val (
                                        buttonText,
                                    ) = createRefs()
                                    TextButton(
                                        modifier = Modifier
                                            .constrainAs(buttonText){
                                                start.linkTo(parent.start)
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                end.linkTo(parent.end)
                                            },
                                        border = BorderStroke(
                                            1.dp,
                                            Color.Blue,
                                        ),
                                        onClick = {},
                                    ){
                                        Text(text = "errore")
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }}


