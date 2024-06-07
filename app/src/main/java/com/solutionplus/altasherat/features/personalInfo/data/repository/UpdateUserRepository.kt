package com.solutionplus.altasherat.features.personalInfo.data.repository

import com.solutionplus.altasherat.features.personalInfo.data.mappers.UpdateUserMapper
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.personalInfo.domain.repository.local.IUpdateUserLocalDS
import com.solutionplus.altasherat.features.personalInfo.domain.repository.remote.IUpdateUserRemoteDS
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.services.user.domain.models.User

internal class UpdateUserRepository(
    private val localDS: IUpdateUserLocalDS,
    private val remoteDS: IUpdateUserRemoteDS
) : IUpdateUserRepository {
    override suspend fun updateUserInfo(updateUserRequest: UpdateUserInfoRequest): UpdateUser {
        val user = remoteDS.updateUser(updateUserRequest)
        return UpdateUserMapper.dtoToDomain(user)
    }

//    override suspend fun saveUpdatedUser(updatedUser: User) {
//        val request = UserMapper.domainToEntity(updatedUser)
//        localDS.savePersonalInfo(request)
//    }

//    override suspend fun getUpdatedUserFromLocal(): User {
//        return UserMapper.entityToDomain(localDS.getPersonalInfo())
//    }

    override suspend fun getUpdatedUserFromRemote(): User {
        val updatedUser = remoteDS.getUpdateUser().user
        return UserMapper.dtoToDomain(updatedUser ?: UserDto())
    }
}