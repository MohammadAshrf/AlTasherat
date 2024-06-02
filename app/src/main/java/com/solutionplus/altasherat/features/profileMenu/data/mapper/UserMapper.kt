package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper

import com.solutionplus.altasherat.features.personalInfo.domain.models.Image
import com.solutionplus.altasherat.features.personalInfo.domain.models.Phone
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.ImageEntity
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.PhoneEntity
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
            phone = PhoneEntity("","","",-1,"",""),
            image = ImageEntity(-1 , "", "", "", "", "","",false, -1),
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
            phone = Phone("","","",-1,"",""),
            image = Image(-1 , "", "", "", "", "","",false, -1),
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
            phone = Phone("" , "", "", -1, "",""),
            image = Image(-1 , "", "", "", "", "","",false, -1),
            birthdate = "",
            emailVerified = false,
            phoneVerified = false,
            blocked = -1,
            country = Country(-1, "", "", "", "", ""),
            allPermissions = emptyList()
        )
    }


}