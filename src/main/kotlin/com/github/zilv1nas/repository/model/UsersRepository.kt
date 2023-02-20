package com.github.zilv1nas.repository.model

import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UsersRepository : PanacheRepository<User>