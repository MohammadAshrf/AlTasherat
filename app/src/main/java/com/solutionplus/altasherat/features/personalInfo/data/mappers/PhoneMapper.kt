package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.PhoneDto
import com.solutionplus.altasherat.features.personalInfo.domain.models.Phone

internal object PhoneMapper : Mapper<PhoneDto, Phone, Unit>() {
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
}