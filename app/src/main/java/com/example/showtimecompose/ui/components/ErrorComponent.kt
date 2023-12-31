package com.example.showtimecompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ErrorComponent() {
    return ConstraintLayout(
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
