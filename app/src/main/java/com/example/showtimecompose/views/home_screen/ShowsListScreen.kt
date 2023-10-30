package com.example.showtimecompose.views.home_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

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
        if(list == null) return@Surface
        LazyVerticalGrid(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                ),
            columns = GridCells.Adaptive(minSize = 100.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(list.itemCount) { index ->
                list[index].let { games ->
                    //val id = games?.id
                    val name = games?.name ?: ""

                    Card(
                        onClick ={},
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Text(
                                text = name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )

                        }
                    }
                }
            }
            list.apply {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            ConstraintLayout(
                            ) {
                                val (
                                    loadingCircular,
                                ) = createRefs()
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .constrainAs(loadingCircular){
                                            start.linkTo(parent.start)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            end.linkTo(parent.end)
                                        },
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
                                    Text(text = "errrore")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
