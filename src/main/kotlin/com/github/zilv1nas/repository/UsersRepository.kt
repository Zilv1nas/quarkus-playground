package com.github.zilv1nas.repository

import com.github.zilv1nas.repository.model.User
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UsersRepository : PanacheRepository<User>