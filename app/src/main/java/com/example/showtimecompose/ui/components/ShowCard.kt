package com.example.showtimecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
fun ShowCard( image: String?){
    var selected = remember { mutableStateOf(false) }
    return Card(
        modifier = Modifier
            .fillMaxWidth(),

        onClick ={},
    ) {
        Box(
            modifier= Modifier
                .fillMaxWidth())
        {
            AsyncImage(
                model = image,
                /* placeholder = painterResource(id = R.drawable.sudoimage),
                 error = painterResource(id = R.drawable.sudoimage),*/
                contentDescription = "The show poster",

                )
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
