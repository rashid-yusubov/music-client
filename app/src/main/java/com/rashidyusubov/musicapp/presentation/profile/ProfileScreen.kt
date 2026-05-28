package com.rashidyusubov.musicapp.presentation.profile

import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.theme.ThemeViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(themeViewModel: ThemeViewModel = hiltViewModel()) {

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Профиль")

        Text(
            text = "Темная тема"
        )

        Switch(
            checked = isDarkTheme,

            onCheckedChange = {

                themeViewModel.setDarkTheme(it)
            }
        )

        Button(
            onClick = {

                FirebaseAuth
                    .getInstance()
                    .signOut()
            }
        ) {

            Text(text = "Выйти")
        }
    }
}