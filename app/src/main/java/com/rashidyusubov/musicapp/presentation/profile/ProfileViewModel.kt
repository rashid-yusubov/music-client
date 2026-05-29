package com.rashidyusubov.musicapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val client: HttpClient
) : ViewModel() {

    private val _user =
        MutableStateFlow<UserDto?>(null)

    val user =
        _user.asStateFlow()

    init {

        loadProfile()
    }

    private fun loadProfile() {

        viewModelScope.launch {

            try {

                val token =
                    FirebaseAuth.getInstance()
                        .currentUser
                        ?.getIdToken(false)
                        ?.await()
                        ?.token
                        ?: return@launch

                _user.value =
                    client.get("${BASE_URL}auth/me") {

                        header(
                            HttpHeaders.Authorization,
                            "Bearer $token"
                        )
                    }.body()

            } catch (_: Exception) {

            }
        }
    }
}