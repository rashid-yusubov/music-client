package com.rashidyusubov.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.navigation.MainNavigation
import com.rashidyusubov.musicapp.presentation.theme.ThemeViewModel
import com.rashidyusubov.musicapp.ui.theme.MusicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val themeViewModel: ThemeViewModel = hiltViewModel()

            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            MusicTheme(
                darkTheme = isDarkTheme
            ) {

                MainNavigation()
            }
        }
    }
}