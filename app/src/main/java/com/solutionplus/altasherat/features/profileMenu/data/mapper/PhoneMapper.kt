package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.PhoneEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.Phone


internal object PhoneMapper : Mapper<Unit, Phone, PhoneEntity>() {
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

    override fun dtoToDomain(model: Unit): Phone {
        return Phone(
            id = -1,
            countryCode = "",
            number = "",
            extension = "",
            type = "",
            holderName = ""
        )
    }
}