package com.example.showtimecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.showtimecompose.network.models.ShowItemModel
import com.example.showtimecompose.views.home_screen.ShowListViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowCard( show: ShowItemModel, viewModel:ShowListViewModel){
    val selected = remember { mutableStateOf(false) }
    return Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(13.dp),
        onClick ={ viewModel.searchShow("t")},
    ) {
        Box(
            modifier= Modifier
                .fillMaxWidth())
        {
            if(show.image!=null)
            SubcomposeAsyncImage(
                model = show.image.original,
                contentDescription = "The show's poster",
                )
                {
                    val state = painter.state
                    when (state) {
                        is AsyncImagePainter.State.Loading -> {
                            LoadingComponent()
                        }

                        is AsyncImagePainter.State.Error -> {
                            Icons.Default.Error
                        }

                        else -> SubcomposeAsyncImageContent()
                    }
                }
            else
                Box(modifier=Modifier.height(100.dp).align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ){
                    Text(text=show.name ?: "", textAlign = TextAlign.Center, modifier=Modifier.fillMaxWidth())
                }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
                    .background(androidx.compose.ui.graphics.Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            )
            {
                    Icon(
                    imageVector = if(!selected.value) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier.padding(4.dp).clip(CircleShape).clickable {
                        selected.value=!selected.value
                        println("Selected: $selected")
                    }
                )

            }

        }
    }
}
