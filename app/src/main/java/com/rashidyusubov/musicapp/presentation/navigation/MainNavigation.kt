package com.rashidyusubov.musicapp.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rashidyusubov.musicapp.presentation.album.details.AlbumDetailsScreen
import com.rashidyusubov.musicapp.presentation.artist.details.ArtistDetailsScreen
import com.rashidyusubov.musicapp.presentation.auth.login.LoginScreen
import com.rashidyusubov.musicapp.presentation.auth.register.RegisterScreen
import com.rashidyusubov.musicapp.presentation.home.HomeScreen
import com.rashidyusubov.musicapp.presentation.library.LibraryScreen
import com.rashidyusubov.musicapp.presentation.list.AlbumsListScreen
import com.rashidyusubov.musicapp.presentation.list.ArtistsListScreen
import com.rashidyusubov.musicapp.presentation.list.PlaylistsListScreen
import com.rashidyusubov.musicapp.presentation.list.TracksListScreen
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.player.components.FullPlayer
import com.rashidyusubov.musicapp.presentation.player.components.MiniPlayer
import com.rashidyusubov.musicapp.presentation.playlist.details.PlaylistDetailsScreen
import com.rashidyusubov.musicapp.presentation.profile.ProfileScreen
import com.rashidyusubov.musicapp.presentation.search.SearchScreen
import com.rashidyusubov.musicapp.presentation.track.TrackDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val currentTrack by playerViewModel.currentTrack.collectAsState()
    
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFullPlayer by remember { mutableStateOf(false) }

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
                Column {
                    if (currentTrack != null) {
                        MiniPlayer(
                            track = currentTrack!!,
                            playerViewModel = playerViewModel,
                            onClick = { showFullPlayer = true }
                        )
                    }
                    BottomBar(navController)
                }
            }
        }
    ) { innerPadding ->
        
        if (showFullPlayer && currentTrack != null) {
            ModalBottomSheet(
                onDismissRequest = { showFullPlayer = false },
                sheetState = sheetState,
                dragHandle = null,
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                FullPlayer(
                    track = currentTrack!!,
                    playerViewModel = playerViewModel,
                    onMinimize = { showFullPlayer = false }
                )
            }
        }

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
                HomeScreen(
                    navController = navController,
                    playerViewModel = playerViewModel
                )
            }

            composable(BottomNavItem.Library.route) {

                LibraryScreen(

                    onTrackClick = { trackId ->

                        navController.navigate("track/$trackId")
                    },
                    onPlaylistsClick = {
                        navController.navigate("playlists")
                    },
                    playerViewModel = playerViewModel
                )
            }

            composable("all_tracks") {
                TracksListScreen(
                    onBackClick = { navController.popBackStack() },
                    onTrackClick = { trackId -> navController.navigate("track/$trackId") },
                    playerViewModel = playerViewModel
                )
            }

            composable("all_artists") {
                ArtistsListScreen(
                    onBackClick = { navController.popBackStack() },
                    onArtistClick = { artistId -> navController.navigate("artist/$artistId") }
                    // playerViewModel = playerViewModel // If ArtistsListScreen needs it
                )
            }

            composable("all_albums") {
                AlbumsListScreen(
                    onBackClick = { navController.popBackStack() },
                    onAlbumClick = { albumId -> navController.navigate("album/$albumId") }
                    // playerViewModel = playerViewModel // If AlbumsListScreen needs it
                )
            }

            composable("playlists") {
                PlaylistsListScreen(
                    onBackClick = { navController.popBackStack() },
                    onPlaylistClick = { playlistId ->
                        // We need the title too, or load it in details
                        navController.navigate("playlist/$playlistId/Плейлист")
                    }
                )
            }

            composable("playlist/{playlistId}/{playlistTitle}") { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId")?.toIntOrNull() ?: 0
                val playlistTitle = backStackEntry.arguments?.getString("playlistTitle") ?: "Плейлист"
                PlaylistDetailsScreen(
                    playlistId = playlistId,
                    playlistTitle = playlistTitle,
                    onBackClick = { navController.popBackStack() },
                    onTrackClick = { trackId -> navController.navigate("track/$trackId") },
                    playerViewModel = playerViewModel
                )
            }

            composable(BottomNavItem.Search.route) {
                SearchScreen(playerViewModel = playerViewModel)
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen()
            }

            composable(route = "track/{trackId}") {

                val trackId =
                    it.arguments
                        ?.getString("trackId")
                        ?.toIntOrNull() ?: 0

                TrackDetailsScreen(
                    trackId = trackId,
                    playerViewModel = playerViewModel
                )
            }

            composable(
                route = "artist/{artistId}"
            ) {

                val artistId =
                    it.arguments
                        ?.getString("artistId")
                        ?.toIntOrNull()
                        ?: 0

                ArtistDetailsScreen(

                    artistId = artistId,

                    onAlbumClick = { albumId ->

                        navController.navigate(
                            "album/$albumId"
                        )
                    },

                    onTrackClick = { trackId ->

                        navController.navigate(
                            "track/$trackId"
                        )
                    },
                    playerViewModel = playerViewModel
                )
            }

            composable(
                route = "album/{albumId}"
            ) {

                val albumId =
                    it.arguments
                        ?.getString("albumId")
                        ?.toIntOrNull()
                        ?: 0

                AlbumDetailsScreen(

                    albumId = albumId,

                    onArtistClick = { artistId ->

                        navController.navigate(
                            "artist/$artistId"
                        )
                    },

                    onTrackClick = { trackId ->

                        navController.navigate(
                            "track/$trackId"
                        )
                    },
                    playerViewModel = playerViewModel
                )
            }
        }
    }
}