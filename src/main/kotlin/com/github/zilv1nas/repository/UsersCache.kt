package com.github.zilv1nas.repository

import com.github.zilv1nas.repository.model.User
import com.github.zilv1nas.service.UsersProjector
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.redis.datasource.RedisDataSource
import io.quarkus.redis.datasource.value.SetArgs
import io.quarkus.redis.datasource.value.ValueCommands
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jboss.logging.Logger
import java.time.Duration
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UsersCache(
    redis: RedisDataSource,
    @ConfigProperty(name = "users.cache.durationSeconds")
    private val cacheDuration: Long,
) {
    private val logger: Logger = Logger.getLogger(UsersProjector::class.java)

    private val commands: ValueCommands<String, CachedUsers> = redis.value(CachedUsers::class.java)

    @WithSpan
    fun getOrCompute(computeUsers: () -> List<User>): List<User> {
        val cachedUsers = getCachedUsers()
        return if (cachedUsers != null) {
            logger.info("Returning users from cache")
            cachedUsers
        } else {
            logger.info("Returning computed users")
            cacheUsers(computeUsers)
        }
    }

    private fun getCachedUsers(): List<User>? = commands.get("users")?.list

    private fun cacheUsers(computeUsers: () -> List<User>): List<User> {
        val users = computeUsers()
        commands.set("users", CachedUsers(users), SetArgs().ex(Duration.ofSeconds(cacheDuration)))
        return users
    }

    private data class CachedUsers(val list: List<User>)
}