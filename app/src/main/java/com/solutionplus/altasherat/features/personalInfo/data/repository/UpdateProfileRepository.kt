package com.solutionplus.altasherat.features.personalInfo.data.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UpdateUserMapper
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UserMapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateProfileLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateProfileRemoteDS

internal class UpdateProfileRepository(
    private val localDS: IUpdateProfileLocalDS,
    private val remoteDS: IUpdateProfileRemoteDS
) : IUpdateProfileRepository {
    override suspend fun updateProfileInfo(remoteRequest: RemoteRequest): UpdateUser {
        val user = remoteDS.updateProfileInfo(remoteRequest)
        return UpdateUserMapper.dtoToDomain(user)
    }

    override suspend fun saveProfileInfo(updatedUser: User) {
        val request = UserMapper.domainToEntity(updatedUser)
        localDS.savePersonalInfo(request)
    }

    override suspend fun getProfileInfoLocal(): User {
        return UserMapper.entityToDomain(localDS.getPersonalInfo())
    }

    override suspend fun getProfileInfoRemote(): User {
        val updatedUser = remoteDS.getProfileInfo().user
        return UserMapper.dtoToDomain(updatedUser ?: UserDto())
    }
}