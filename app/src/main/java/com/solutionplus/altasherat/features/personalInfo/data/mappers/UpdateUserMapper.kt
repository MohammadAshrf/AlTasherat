package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UpdateUserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UpdateUserEntity
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser

internal object UpdateUserMapper : Mapper<UpdateUserDto, UpdateUser, UpdateUserEntity>() {
    override fun dtoToDomain(model: UpdateUserDto): UpdateUser {
        return UpdateUser(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: UserDto())
        )
    }
}