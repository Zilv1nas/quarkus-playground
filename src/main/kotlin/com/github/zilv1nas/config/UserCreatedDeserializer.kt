package com.github.zilv1nas.config

import com.github.zilv1nas.service.model.UserCreated
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

class UserCreatedDeserializer : ObjectMapperDeserializer<UserCreated>(UserCreated::class.java)