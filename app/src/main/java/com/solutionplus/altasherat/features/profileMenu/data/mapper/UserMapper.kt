package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.User

internal object UserMapper : Mapper<Unit,User, UserEntity>() {

    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            userName = model.userName.orEmpty(),
            firsName = model.firstName.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastName = model.lastName.orEmpty(),
            phone = model.phone.orEmpty(),
            birthDate = model.birthDate.orEmpty(),
            imageUrl = model.imageUrl.orEmpty(),
            fullName = model.fullName.orEmpty(),
            email = model.email.orEmpty(),
        )
    }
    override fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            userName = model.userName,
            firstName = model.firsName,
            middleName = model.middleName,
            lastName = model.lastName,
            phone = model.phone,
            birthDate = model.birthDate,
            imageUrl = model.imageUrl,
            fullName = model.fullName,
            email = model.email,
            password = ""
        )
    }

    override fun dtoToDomain(model: Unit): User {
        return User(
            id = 0,
            userName = "",
            fullName = "",
            email = "",
            password = "",
            firstName = "",
            middleName = "",
            lastName = "",
            phone = "",
            birthDate = "",
            imageUrl = "",
        )
    }
}