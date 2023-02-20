package com.github.zilv1nas.service

import com.github.zilv1nas.service.model.UserCreated
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.jboss.logging.Logger
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserEventsProducer(
    @Channel("users-out")
    private val userEventsEmitter: Emitter<UserCreated>,
) {
    private val logger: Logger = Logger.getLogger(UserEventsProducer::class.java)

    fun publish(event: UserCreated): Uni<Void> {
        logger.info("Publishing event: $event")
        return Uni.createFrom().completionStage(userEventsEmitter.send(event))
    }
}