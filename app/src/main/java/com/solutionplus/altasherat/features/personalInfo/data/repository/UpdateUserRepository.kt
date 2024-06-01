package com.solutionplus.altasherat.features.personalInfo.data.repository

import com.solutionplus.altasherat.features.personalInfo.data.mappers.UpdateUserMapper
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UserMapper
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS

internal class UpdateUserRepository(
    private val localDS: IUpdateUserLocalDS,
    private val remoteDS: IUpdateUserRemoteDS
) : IUpdateUserRepository {
    override suspend fun updateUser(updateUserRequest: UpdateUserRequest): UpdateUser {
        val user = remoteDS.updateUser(updateUserRequest)
        return UpdateUserMapper.dtoToDomain(user)
    }

    override suspend fun saveUpdatedUser(updatedUser: UpdateUser) {
        val request = UpdateUserMapper.domainToEntity(updatedUser)
        localDS.savePersonalInfo(request)
    }

    override suspend fun getUpdatedUserFromLocal(): User {
        return UserMapper.entityToDomain(localDS.getPersonalInfo())
    }

    override suspend fun getUpdatedUserFromRemote(): User {
        val updatedUser = remoteDS.getUpdateUser()
        return UserMapper.dtoToDomain(updatedUser)
    }

    override suspend fun hasUser(): Boolean {
        return localDS.hasUserInfo()
    }
}