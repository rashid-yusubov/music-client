package com.rashidyusubov.musicapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    data object Home : BottomNavItem(
        route = "home",
        title = "Главная",
        icon = Icons.Outlined.Home
    )

    data object Library : BottomNavItem(
        route = "library",
        title = "Библиотека",
        icon = Icons.Outlined.LibraryMusic
    )

    data object Search : BottomNavItem(
        route = "search",
        title = "Поиск",
        icon = Icons.Outlined.Search
    )

    data object Profile : BottomNavItem(
        route = "profile",
        title = "Профиль",
        icon = Icons.Outlined.Person
    )

    data object Login : BottomNavItem(
        route = "login",
        title = "Login",
        icon = Icons.Outlined.Person
    )

    data object Register : BottomNavItem(
        route = "register",
        title = "Register",
        icon = Icons.Outlined.Person
    )
}