package com.rashidyusubov.musicapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorContent(
    message: String,
    onRetry: (() -> Unit)? = null
) {

    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = message
        )

        if (onRetry != null) {

            Button(
                onClick = onRetry
            ) {

                Text(
                    text = "Повторить"
                )
            }
        }
    }
}