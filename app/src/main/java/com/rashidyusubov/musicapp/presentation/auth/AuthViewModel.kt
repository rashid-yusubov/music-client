package com.rashidyusubov.musicapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

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
}