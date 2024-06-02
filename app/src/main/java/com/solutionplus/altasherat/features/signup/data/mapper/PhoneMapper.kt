package com.solutionplus.altasherat.features.signup.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.signup.data.model.dto.PhoneDto
import com.solutionplus.altasherat.features.signup.data.model.entity.PhoneEntity
import com.solutionplus.altasherat.features.signup.domain.model.Phone

internal object PhoneMapper : Mapper<PhoneDto, Phone, PhoneEntity>() {
    override fun dtoToDomain(model: PhoneDto): Phone {
        return Phone(
            id = model.id ?: -1,
            countryCode = model.countryCode.orEmpty(),
            number = model.number.orEmpty(),
            extension = model.extension.orEmpty(),
            type = model.type.orEmpty(),
            holderName = model.holderName.orEmpty()
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
}