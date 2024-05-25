package com.solutionplus.altasherat.features.signup.data.mapper

import com.solutionplus.altasherat.features.signup.data.model.dto.UserDto
import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.common.data.mapper.Mapper

internal object UserMapper : Mapper<UserDto, User, UserEntity>() {

    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: 0,
            userName = model.username.orEmpty(),
            fullName = "${model.firstname.orEmpty()} ${model.lastname.orEmpty()}",
            email = model.email.orEmpty(),
            lastName = model.lastname.orEmpty(),
            firstName = model.firstname.orEmpty(),
            middleName = model.middlename.orEmpty(),
            birthDate = model.birthdate.orEmpty(),
            phone = model.phone?.countryCode.orEmpty() + "-" + model.phone?.number.orEmpty(),
            emailVerified = model.emailVerified ?: false

        )
    }
    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id ?: 0,
            userName = model.userName.orEmpty(),
            firsName = model.firstName.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastName = model.lastName.orEmpty(),
            phone = model.phone.orEmpty(),
            birthDate = model.birthDate.orEmpty(),
            imageUrl = model.imageUrl.orEmpty(),
            fullName = model.fullName.orEmpty(),
            email = model.email.orEmpty(),
            emailVerified = model.emailVerified ?: false
        )
    }
}


