package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ProfileInfoDto
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UpdateProfileInfoEntity
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateProfileInfo
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto

internal object UpdateProfileInfoMapper :
    Mapper<ProfileInfoDto, UpdateProfileInfo, UpdateProfileInfoEntity>() {
    override fun dtoToDomain(model: ProfileInfoDto): UpdateProfileInfo {
        return UpdateProfileInfo(
            message = model.message.orEmpty(),
            user = UserMapper.dtoToDomain(model.user ?: UserDto())
        )
    }
}