package com.solutionplus.altasherat.features.personalInfo.data.repository

import com.solutionplus.altasherat.common.domain.models.request.RemoteRequest
import com.solutionplus.altasherat.features.personalInfo.data.mappers.UpdateProfileInfoMapper
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateProfileInfo
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.services.user.domain.models.User
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateProfileRemoteDS

internal class UpdateProfileRepository(
    private val remoteDS: IUpdateProfileRemoteDS
) : IUpdateProfileRepository {
    override suspend fun updateProfileInfo(remoteRequest: RemoteRequest): UpdateProfileInfo {
        val user = remoteDS.updateProfileInfo(remoteRequest)
        return UpdateProfileInfoMapper.dtoToDomain(user)
    }

    override suspend fun getProfileInfoRemote(): User {
        val updatedUser = remoteDS.getProfileInfo().user
        return UserMapper.dtoToDomain(updatedUser ?: UserDto())
    }
}