package com.rashidyusubov.musicapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rashidyusubov.musicapp.presentation.auth.login.LoginScreen
import com.rashidyusubov.musicapp.presentation.auth.register.RegisterScreen
import com.rashidyusubov.musicapp.presentation.home.HomeScreen
import com.rashidyusubov.musicapp.presentation.library.LibraryScreen
import com.rashidyusubov.musicapp.presentation.profile.ProfileScreen
import com.rashidyusubov.musicapp.presentation.search.SearchScreen

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    var currentUser by remember {
        mutableStateOf(FirebaseAuth.getInstance().currentUser)
    }

    LaunchedEffect(Unit) {

        FirebaseAuth
            .getInstance()
            .addAuthStateListener { auth ->

                currentUser = auth.currentUser
            }
    }

    val isAuthorized = currentUser != null

    Scaffold(
        bottomBar = {

            if (isAuthorized) {

                BottomBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,

            startDestination = if (isAuthorized) {
                BottomNavItem.Home.route
            } else {
                BottomNavItem.Login.route
            },

            modifier = Modifier.padding(innerPadding)
        ) {

            composable(BottomNavItem.Login.route) {

                LoginScreen(

                    onNavigateToRegister = {

                        navController.navigate(
                            BottomNavItem.Register.route
                        )
                    },

                    onAuthorized = {

                        navController.navigate(
                            BottomNavItem.Home.route
                        ) {

                            popUpTo(0)
                        }
                    }
                )
            }

            composable(BottomNavItem.Register.route) {

                RegisterScreen(

                    onNavigateBack = {

                        navController.popBackStack()
                    },

                    onAuthorized = {

                        navController.navigate(
                            BottomNavItem.Home.route
                        ) {

                            popUpTo(0)
                        }
                    }
                )
            }

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