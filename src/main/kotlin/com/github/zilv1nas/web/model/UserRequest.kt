package com.github.zilv1nas.web.model

import com.github.zilv1nas.service.model.UserCreated
import java.util.UUID

data class UserRequest(val name: String, val email: String) {
    fun toEvent(id: UUID) = UserCreated(id, name, email)
}
