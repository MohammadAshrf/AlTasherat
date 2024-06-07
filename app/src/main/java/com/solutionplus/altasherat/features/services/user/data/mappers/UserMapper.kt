package com.solutionplus.altasherat.features.services.user.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.country.data.mappers.CountryMapper
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.services.user.data.models.dto.UserDto
import com.solutionplus.altasherat.features.services.user.data.models.entity.UserEntity
import com.solutionplus.altasherat.features.services.user.domain.models.User


internal object UserMapper : Mapper<UserDto, User, UserEntity>() {
    override fun dtoToDomain(model: UserDto): User {
        return User(
            id = model.id ?: -1,
            username = model.username.orEmpty(),
            firstname = model.firstname.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            phone = PhoneMapper.dtoToDomain(model.phone ?: PhoneDto()),
            image = ImageMapper.dtoToDomain(model.image ?: ImageDto()),
            birthdate = model.birthdate.orEmpty(),
            emailVerified = model.emailVerified ?: false,
            phoneVerified = model.phoneVerified ?: false,
            blocked = model.blocked ?: -1,
            country = CountryMapper.dtoToDomain(model.country ?: CountryDto()),
            allPermissions = model.allPermissions.orEmpty()
        )
    }
    override fun domainToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id ?: -1,
            username = model.username.orEmpty(),
            firstname = model.firstname.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastname = model.lastname.orEmpty(),
            email = model.email.orEmpty(),
            phone = PhoneMapper.domainToEntity(model.phone!!),
            image = ImageMapper.domainToEntity(model.image!!),
            birthdate = model.birthdate.orEmpty(),
            emailVerified = model.emailVerified!!,
            phoneVerified = model.phoneVerified!!,
            blocked = model.blocked!!,
            country = CountryMapper.domainToEntity(model.country!!),
            allPermissions = model.allPermissions.orEmpty(),
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
}
