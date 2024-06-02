package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper

import com.solutionplus.altasherat.features.profileMenu.data.model.entity.PhoneEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.Phone
import com.solutionplus.altasherat.features.signup.data.model.dto.PhoneDto
/*

internal object PhoneMapper : Mapper<PhoneDto, Phone, Unit>() {

    override fun dtoToDomain(model: PhoneDto): Phone {
        return Phone(
            id = model.id ?: -1,
            countryCode = model.countryCode.orEmpty(),
            number = model.number.orEmpty(),
            extension = model.extension.toString(),
            type = model.type.toString(),
            holderName = model.holderName.toString()
        )
    }

    override fun domainToEntity(model: Phone): PhoneEntity {
        return PhoneEntity(
            id = model.id,
            countryCode = model.countryCode,
            number = model.number,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }

    override fun entityToDomain(model: PhoneEntity): Phone {
        return Phone(
            id = model.id,
            countryCode = model.countryCode,
            number = model.number,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }
}*/
