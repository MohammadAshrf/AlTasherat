package com.solutionplus.altasherat.features.deleteaccount.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.changepassword.data.model.dto.ChangePasswordDto
import com.solutionplus.altasherat.features.changepassword.data.model.entity.ChangePasswordEntity
import com.solutionplus.altasherat.features.changepassword.domain.model.ChangePasswordRequest
import com.solutionplus.altasherat.features.deleteaccount.data.model.dto.DeleteAccountDto
import com.solutionplus.altasherat.features.deleteaccount.data.model.entity.DeleteAccountEntity
import com.solutionplus.altasherat.features.deleteaccount.domain.model.request.DeleteAccountRequest

internal object DeleteAccountMapper : Mapper<DeleteAccountDto, DeleteAccountRequest, DeleteAccountEntity>() {

    override fun dtoToDomain(model: DeleteAccountDto): DeleteAccountRequest {
        return DeleteAccountRequest(
            password = model.password.orEmpty()
        )
    }

    override fun domainToEntity(model: DeleteAccountRequest): DeleteAccountEntity {
        return DeleteAccountEntity(
            password  = model.password.orEmpty()
        )
    }
}