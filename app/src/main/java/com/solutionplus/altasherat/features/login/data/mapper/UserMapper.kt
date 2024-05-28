package com.solutionplus.altasherat.features.login.data.mapper

import com.solutionplus.altasherat.features.login.data.model.dto.UserDto
import com.solutionplus.altasherat.features.login.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.login.domain.model.User
import com.solutionplus.altasherat.common.data.mapper.Mapper

internal object UserMapper : Mapper<UserDto, User, UserEntity>() {

    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: 0,
            userName = model.username.orEmpty(),
            fullName = "${model.firstname.orEmpty()} ${model.lastname.orEmpty()}",
            email = model.email.orEmpty(),
            password = "",
            lastName = model.lastname.orEmpty(),
            firstName = model.firstname.orEmpty(),
            middleName = model.middlename.orEmpty(),
            birthDate = model.birthdate.orEmpty(),
            phone = model.phone?.countryCode.orEmpty() + "-" + model.phone?.number.orEmpty(),
            imageUrl = "",
            emailVerified = model.emailVerified ?: false

        )
    }

    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            userName = model.userName,
            firsName = model.firstName,
            middleName = model.middleName,
            lastName = model.lastName,
            phone = model.phone,
            birthDate = model.birthDate.orEmpty(),
            imageUrl = model.imageUrl,
            fullName = model.fullName,
            email = model.email,
            emailVerified = model.emailVerified
        )
    }
}


