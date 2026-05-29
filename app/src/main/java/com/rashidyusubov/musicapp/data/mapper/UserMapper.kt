package com.rashidyusubov.musicapp.data.mapper

import com.rashidyusubov.musicapp.data.remote.dto.UserDto
import com.rashidyusubov.musicapp.domain.model.User

fun UserDto.toDomain(): User {

    return User(
        id = id,
        email = email,
        username = username,
        avatarUrl = avatarUrl
    )
}