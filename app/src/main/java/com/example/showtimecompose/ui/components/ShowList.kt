package com.example.showtimecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.showtimecompose.network.models.ShowItemModel
import com.example.showtimecompose.views.home_screen.ShowListViewModel

@Composable
fun ShowsList(
    showList: List<ShowItemModel>,
    viewModel: ShowListViewModel,
    navController: NavController
) {
    LazyVerticalStaggeredGrid(

        columns = StaggeredGridCells.Fixed(2),
        //verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,

        ) {
        items(showList) { showItemModel ->
            ShowCard(show = showItemModel, viewModel, navController)

        }

    }
}
