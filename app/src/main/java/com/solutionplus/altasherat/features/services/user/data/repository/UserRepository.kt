package com.solutionplus.altasherat.features.services.user.data.repository

import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.services.user.domain.repository.IUserRepository
import com.solutionplus.altasherat.features.services.user.domain.repository.Local.IUserLocalDS


internal class UserRepository (private val localDs: IUserLocalDS): IUserRepository {
    override suspend fun saveUser(user: User) {
        val result = UserMapper.domainToEntity(user)
        localDs.saveUser(result)
    }

    override suspend fun getUser(): UserEntity =
        localDs.getUser()

}