package com.github.zilv1nas.web

import com.github.zilv1nas.repository.UsersRepository
import com.github.zilv1nas.repository.model.User
import com.github.zilv1nas.service.UserEventsProducer
import com.github.zilv1nas.web.model.UserRequest
import com.github.zilv1nas.web.model.UsersResponse
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.metrics.MetricUnits
import org.eclipse.microprofile.metrics.annotation.Counted
import org.eclipse.microprofile.metrics.annotation.Timed
import org.jboss.logging.Logger
import java.util.UUID
import javax.transaction.Transactional
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/v1/users")
class UsersController(
    private val usersRepository: UsersRepository,
    private val userEventsProducer: UserEventsProducer,
) {
    private val logger: Logger = Logger.getLogger(UsersController::class.java)

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "usersListCount", description = "How many times user list was requested")
    @Timed(
        name = "usersListTimer",
        description = "A measure of how long it takes to fetch users list",
        unit = MetricUnits.MILLISECONDS
    )
    fun getUsers(): UsersResponse = usersRepository
        .findAll()
        .list<User>()
        .let { UsersResponse.from(it) }

    @POST
    @Transactional
    @Counted(name = "createUsersCount", description = "How many users were created via API")
    @Timed(
        name = "createUsersTimer",
        description = "A measure of how long it takes to create a user",
        unit = MetricUnits.MILLISECONDS
    )
    fun saveUser(request: UserRequest): Uni<Void> {
        logger.info("New user request received: $request")
        return userEventsProducer.publish(request.toEvent(UUID.randomUUID()))
    }
}