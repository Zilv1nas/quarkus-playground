package com.github.zilv1nas.service

import com.github.zilv1nas.repository.UsersRepository
import com.github.zilv1nas.service.model.UserCreated
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class UsersProjector(
    private val usersRepository: UsersRepository,
) {
    private val logger: Logger = Logger.getLogger(UsersProjector::class.java)

    @Incoming("users")
    @Transactional
    fun on(event: UserCreated) {
        logger.info("Consuming event: $event")
        usersRepository.persist(event.toUser())
    }
}