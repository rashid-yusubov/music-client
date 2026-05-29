package com.rashidyusubov.musicapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rashidyusubov.musicapp.core.network.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val client: HttpClient) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _state =
        MutableStateFlow(AuthState())

    val state =
        _state.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null
                    )

                auth.signInWithEmailAndPassword(
                    email,
                    password
                ).await()

                syncUserWithServer()

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        isAuthorized = true
                    )
                
            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
            }
        }
    }

    fun register(
        email: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null
                    )

                auth.createUserWithEmailAndPassword(
                    email,
                    password
                ).await()

                syncUserWithServer()

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        isAuthorized = true
                    )

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
            }
        }
    }

    private suspend fun syncUserWithServer() {

        val token = auth.currentUser
            ?.getIdToken(false)
            ?.await()
            ?.token
            ?: return

        client.get("${BASE_URL}auth/me") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}