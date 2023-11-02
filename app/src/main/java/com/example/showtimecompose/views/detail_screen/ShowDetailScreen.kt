package com.example.showtimecompose.views.detail_screen

import android.R.id.input
import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.showtimecompose.ui.components.ErrorComponent
import com.example.showtimecompose.ui.components.LoadingComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreen(showId: String) {
    val viewModel: ShowDetailViewModel = hiltViewModel()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var showTitle = remember { mutableStateOf("") }
    viewModel.onEvent(UIDetailEvent.OnSearch(id = showId))
    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        backgroundColor = MaterialTheme.colorScheme.background,


        ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(
                    paddingValues
                )
        ) {
            item {
                when (val state = viewModel.showDetailState.collectAsStateWithLifecycle().value) {
                    is UIDetailState.Loading -> {
                        LoadingComponent()
                    }

                    is UIDetailState.Content -> {
                        var show = state.data
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val showDateYear =
                            if (show.premiered != null) LocalDate.parse(
                                show.premiered,
                                formatter
                            ).year.toString() else ""
                        var showGenres: String = ""
                        show.genres?.mapIndexed { index, genre ->
                            showGenres += genre
                            val size = show.genres!!.size
                            if (size > 1 && index != size - 1) {
                                showGenres += ", "
                            }
                        }
                        val showSummary =
                            Html.fromHtml(show.summary, Html.FROM_HTML_MODE_LEGACY).toString()
                        val configuration = LocalConfiguration.current
                        val screenHeight = configuration.screenHeightDp
                        Box(
                            modifier = Modifier.height((screenHeight).dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            SubcomposeAsyncImage(
                                model = show.image?.original,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = "The show's poster",
                            )
                            {
                                val state = painter.state
                                when (state) {
                                    is AsyncImagePainter.State.Loading -> {
                                        LoadingComponent(modifier = Modifier.padding(30.dp))
                                    }

                                    is AsyncImagePainter.State.Error -> {

                                    }

                                    else -> {
                                        SubcomposeAsyncImageContent()
                                    }

                                }
                            }
                            Card(
                                modifier = Modifier.height((screenHeight / 1.8).dp).fillMaxWidth(),
                                backgroundColor = Color.Black.copy(0.7f),
                                shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),

                                ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Text(
                                        text = show.name ?: "",
                                        color = Color.White,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = showDateYear,
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Thin
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Row() {
                                        Text(text = "Genres: ", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text(text = showGenres, color = Color.White, fontWeight = FontWeight.Normal)
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(text = "Summary: ", color = Color.White, fontWeight = FontWeight.Bold)
                                    Text(text = showSummary, color = Color.White, fontWeight = FontWeight.Normal)
                                    Text(text = showSummary, color = Color.White, fontWeight = FontWeight.Normal)


                                }
                            }
                        }


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
}

