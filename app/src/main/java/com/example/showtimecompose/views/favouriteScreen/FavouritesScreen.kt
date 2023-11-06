package com.example.showtimecompose.views.favouriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.showtimecompose.ui.components.ErrorComponent
import com.example.showtimecompose.ui.components.ShowsList
import com.example.showtimecompose.ui.components.TopAppBar
import com.example.showtimecompose.views.home_screen.ShowListViewModel
import com.example.showtimecompose.views.home_screen.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    viewModel: ShowListViewModel= hiltViewModel(),
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(scrollBehavior, title = "Preferred Shows", navController = navController)
        },

        ) { paddingValues ->
            Column(
                modifier=Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    when(val state = viewModel.showsState.collectAsStateWithLifecycle().value){
                        is UIState.Loading->{}
                        is UIState.Content->{
                            ShowsList(showList=state.data,viewModel,navController )
                        }
                        else->{
                            ErrorComponent()
                        }
                    }

            }
    }

}
