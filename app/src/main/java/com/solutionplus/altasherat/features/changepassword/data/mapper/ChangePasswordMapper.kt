package com.solutionplus.altasherat.features.changepassword.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.data.model.entity.ChangePasswordEntity
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest

internal object ChangePasswordMapper : Mapper<ChangePasswordDto,ChangePasswordRequest, ChangePasswordEntity>() {

    override fun dtoToDomain(model: ChangePasswordDto): ChangePasswordRequest {
        return ChangePasswordRequest(
            oldPassword = model.oldPassword.orEmpty(),
            newPassword = model.newPassword.orEmpty(),
            newPasswordConfirmation = model.newPasswordConfirmation.orEmpty(),
            token = model.token.orEmpty()
        )
    }

    override fun domainToEntity(model: ChangePasswordRequest): ChangePasswordEntity {
        return ChangePasswordEntity(
            oldPassword = model.oldPassword.orEmpty(),
            newPassword = model.newPassword.orEmpty(),
            newPasswordConfirmation = model.newPasswordConfirmation.orEmpty(),
            token = model.token.orEmpty()
        )
    }
}