package com.rashidyusubov.musicapp.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseTokenProvider @Inject constructor() {

    suspend fun getToken(): String? {

        return FirebaseAuth
            .getInstance()
            .currentUser
            ?.getIdToken(true)
            ?.await()
            ?.token
    }
}