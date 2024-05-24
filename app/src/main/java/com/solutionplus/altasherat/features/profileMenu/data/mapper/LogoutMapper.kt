package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.profileMenu.data.model.dto.LogoutDto
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.Logout
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

internal object LogoutMapper : Mapper<LogoutDto, Logout, Unit>() {
    override fun dtoToDomain(model: LogoutDto): Logout {
        return Logout(
            message = model.message.orEmpty()
        )
    }
}