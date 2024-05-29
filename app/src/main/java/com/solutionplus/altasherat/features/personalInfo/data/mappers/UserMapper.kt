package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.UserDto
import com.solutionplus.altasherat.features.personalInfo.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.personalInfo.domain.models.User

internal object UserMapper : Mapper<UserDto, User, UserEntity>() {
    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: -1,
            userName = model.userName.orEmpty(),
            email = model.email.orEmpty(),
            firstName = model.firstName.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastName = model.lastName.orEmpty(),
            birthDate = model.birthDate.orEmpty(),
            emailVerified = model.emailVerified ?: false,
            phoneVerified = model.phoneVerified ?: false,
            isBlocked = model.isBlocked ?: -1,
            phone = "${model.phone?.countryCode.orEmpty()} ${model.phone?.number.orEmpty()}",
            image = "${model.image?.title} ${model.image?.type.orEmpty()} ${model.image?.path}"

        )
    }

    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            userName = model.userName,
            email = model.email,
            firstName = model.firstName,
            middleName = model.middleName,
            lastName = model.lastName,
            birthDate = model.birthDate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            isBlocked = model.isBlocked,
            phone = model.phone,
            image = model.image
        )
    }

    override fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            userName = model.userName,
            email = model.email,
            firstName = model.firstName,
            middleName = model.middleName,
            lastName = model.lastName,
            birthDate = model.birthDate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            isBlocked = model.isBlocked,
            phone = model.phone,
            image = model.image
        )
    }
}
