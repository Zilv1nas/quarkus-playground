package com.github.zilv1nas.service.model

import com.github.zilv1nas.repository.model.User
import java.util.UUID

data class UserCreated(val id: UUID, val name: String, val email: String) {
    fun toUser() = User(id, name, email)
}