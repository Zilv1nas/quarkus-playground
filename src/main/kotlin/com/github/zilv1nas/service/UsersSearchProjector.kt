package com.github.zilv1nas.service

import com.github.zilv1nas.repository.UsersSearchRepository
import com.github.zilv1nas.service.model.UserCreated
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UsersSearchProjector(
    private val searchRepository: UsersSearchRepository,
) {
    private val logger: Logger = Logger.getLogger(UsersSearchProjector::class.java)

    @Incoming("users-search")
    fun on(event: UserCreated) {
        logger.info("Consuming event: $event")
        searchRepository.index(event.toUser())
    }
}