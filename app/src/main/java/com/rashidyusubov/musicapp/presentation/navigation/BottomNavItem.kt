package com.rashidyusubov.musicapp.presentation.navigation

sealed class BottomNavItem(
    val route: String,
    val title: String
) {

    data object Home : BottomNavItem(
        route = "home",
        title = "Главная"
    )

    data object Library : BottomNavItem(
        route = "library",
        title = "Библиотека"
    )

    data object Search : BottomNavItem(
        route = "search",
        title = "Поиск"
    )

    data object Profile : BottomNavItem(
        route = "profile",
        title = "Профиль"
    )
}