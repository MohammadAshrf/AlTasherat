package com.solutionplus.altasherat.features.changepassword.data.repository

import com.solutionplus.altasherat.features.changepassword.data.mapper.ChangePasswordMapper
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.changepassword.domain.repository.IchangePasswordRepository
import com.solutionplus.altasherat.features.changepassword.domain.repository.local.IChangePasswordLocalDS
import com.solutionplus.altasherat.features.changepassword.domain.repository.remote.IChangePasswordRemoteDS
import javax.inject.Inject

internal class ChangePasswordRepository @Inject constructor(
    private val remoteDs: IChangePasswordRemoteDS,
    private val localDS: IChangePasswordLocalDS
) : IchangePasswordRepository {
    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ChangePasswordRequest {
        return ChangePasswordMapper.dtoToDomain(remoteDs.changePassword(changePasswordRequest)!!)
    }

    override suspend fun getAccessToKen(): String = localDS.getAccessToken()
}