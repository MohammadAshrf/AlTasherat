package com.solutionplus.altasherat.features.services.user.data.repository

import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.local.IUserLocalDS


internal class UserRepository (private val localDs: IUserLocalDS): IUserRepository {
    override suspend fun saveUser(user: User) {
        val result = UserMapper.domainToEntity(user)
        localDs.saveUser(result)
    }

    override suspend fun getUserLocal(): User {
        val result = localDs.getUserLocal()
      return  UserMapper.entityToDomain(result)
    }


}