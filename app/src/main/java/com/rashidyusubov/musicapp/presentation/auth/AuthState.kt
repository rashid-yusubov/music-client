package com.rashidyusubov.musicapp.presentation.auth

data class AuthState(

    val isLoading: Boolean = false,

    val isAuthorized: Boolean = false,

    val error: String? = null
)