package com.rashidyusubov.musicapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rashidyusubov.musicapp.presentation.home.HomeScreen
import com.rashidyusubov.musicapp.presentation.library.LibraryScreen
import com.rashidyusubov.musicapp.presentation.profile.ProfileScreen
import com.rashidyusubov.musicapp.presentation.search.SearchScreen

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }

            composable(BottomNavItem.Library.route) {
                LibraryScreen()
            }

            composable(BottomNavItem.Search.route) {
                SearchScreen()
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen()
            }
        }
    }
}