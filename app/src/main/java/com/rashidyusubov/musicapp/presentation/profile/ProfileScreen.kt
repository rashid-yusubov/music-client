package com.rashidyusubov.musicapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.rashidyusubov.musicapp.presentation.theme.ThemeViewModel

@Composable
fun ProfileScreen(
    themeViewModel: ThemeViewModel = hiltViewModel()
) {

    val isDarkTheme by
    themeViewModel
        .isDarkTheme
        .collectAsState()

    val viewModel: ProfileViewModel = hiltViewModel()

    val user by viewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        if (!user?.avatarUrl.isNullOrBlank()) {

            AsyncImage(
                model = user?.avatarUrl,

                contentDescription = null,

                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

        } else {

            Icon(
                imageVector =
                    Icons.Outlined.Person,

                contentDescription = null,

                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = user?.username ?: "",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = user?.email ?: ""
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "Тёмная тема"
                )

                Switch(
                    checked = isDarkTheme,

                    onCheckedChange = {

                        themeViewModel
                            .setDarkTheme(it)
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                FirebaseAuth
                    .getInstance()
                    .signOut()
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Выйти"
            )
        }
    }
}