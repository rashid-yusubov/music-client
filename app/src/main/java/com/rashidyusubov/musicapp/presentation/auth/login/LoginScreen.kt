package com.rashidyusubov.musicapp.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.auth.AuthViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onAuthorized: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(state.isAuthorized) {

        if (state.isAuthorized) {
            onAuthorized()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Вход")

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Email")
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Пароль")
            }
        )

        Button(
            onClick = {

                viewModel.login(
                    email = email,
                    password = password
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Войти")
        }

        Button(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Регистрация")
        }

        if (state.isLoading) {

            CircularProgressIndicator()
        }

        state.error?.let {

            Text(text = it)
        }
    }
}