package com.github.zilv1nas.web

import com.github.zilv1nas.repository.model.User
import com.github.zilv1nas.repository.model.UsersRepository
import com.github.zilv1nas.web.model.UserRequest
import com.github.zilv1nas.web.model.UsersResponse
import java.util.UUID
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/v1/users")
class UsersController(private val usersRepository: UsersRepository) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getUsers(): UsersResponse = usersRepository
        .findAll()
        .list<User>()
        .let { UsersResponse.from(it) }

    @POST
    @Transactional
    fun saveUser(request: UserRequest) {
        usersRepository.persist(request.toUser(UUID.randomUUID()))
    }
}