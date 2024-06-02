package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.User
import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal object UserMapper : Mapper<Unit, User, UserEntity>() {

    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middleName = model.middleName,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.domainToEntity(model.phone),
            image = ImageMapper.domainToEntity(model.image),
            birthdate = model.birthdate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            blocked = model.blocked,
            country = CountryMapper.domainToEntity(model.country),
            allPermissions = model.allPermissions
        )
    }

    override fun entityToDomain(model: UserEntity): User {
        return User(
            id = model.id,
            username = model.username,
            firstname = model.firstname,
            middleName = model.middleName,
            lastname = model.lastname,
            email = model.email,
            phone = PhoneMapper.entityToDomain(model.phone),
            image = ImageMapper.entityToDomain(model.image),
            birthdate = model.birthdate,
            emailVerified = model.emailVerified,
            phoneVerified = model.phoneVerified,
            blocked = model.blocked,
            country = CountryMapper.entityToDomain(model.country),
            allPermissions = model.allPermissions
        )
    }

    override fun dtoToDomain(model: Unit): User {
        return User(
            id = -1,
            username = "",
            firstname = "",
            middleName = "",
            lastname = "",
            email = "",
            phone = PhoneMapper.dtoToDomain(Unit),
            image = ImageMapper.dtoToDomain(Unit),
            birthdate = "",
            emailVerified = false,
            phoneVerified = false,
            blocked = -1,
            country = Country(
                id = -1,
                name = "",
                code = "",
                currency = "",
                phoneCode = "",
                flag = "",
            ),
            allPermissions = emptyList()
        )
    }


}