package com.solutionplus.altasherat.features.personalInfo.data.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UpdateUserMapper
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UserMapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS

internal class UpdateUserRepository(
    private val localDS: IUpdateUserLocalDS,
    private val remoteDS: IUpdateUserRemoteDS
) : IUpdateUserRepository {
    override suspend fun updateUserInfo(remoteRequest: RemoteRequest): UpdateUser {
        val user = remoteDS.updateUser(remoteRequest)
        return UpdateUserMapper.dtoToDomain(user)
    }

    override suspend fun saveUpdatedUser(updatedUser: User) {
        val request = UserMapper.domainToEntity(updatedUser)
        localDS.savePersonalInfo(request)
    }

    override suspend fun getUpdatedUserFromLocal(): User {
        return UserMapper.entityToDomain(localDS.getPersonalInfo())
    }

    override suspend fun getUpdatedUserFromRemote(): User {
        val updatedUser = remoteDS.getUpdateUser().user
        return UserMapper.dtoToDomain(updatedUser ?: UserDto())
    }
}