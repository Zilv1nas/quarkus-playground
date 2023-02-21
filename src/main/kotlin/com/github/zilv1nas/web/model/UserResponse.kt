package com.github.zilv1nas.web.model

import com.github.zilv1nas.repository.model.User
import java.util.UUID

data class UserResponse(val id: UUID, val name: String, val email: String) {
    companion object {
        fun from(user: User) = UserResponse(user.id, user.name, user.email)
    }
}
