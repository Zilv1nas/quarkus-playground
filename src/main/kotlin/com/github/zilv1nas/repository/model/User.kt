package com.github.zilv1nas.repository.model

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: UUID,
    val name: String,
    val email: String,
)
