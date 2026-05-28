package com.rashidyusubov.musicapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController
) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Library,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,

                onClick = {

                    navController.navigate(item.route) {

                        popUpTo(
                            navController.graph.startDestinationId
                        )

                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}