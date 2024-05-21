package com.solutionplus.altasherat.feature.services.country.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.feature.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.feature.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.feature.services.country.domain.models.Country

internal object CountryMapper : Mapper<CountryDto, Country, CountryEntity>() {
    override fun dtoToDomain(model: CountryDto): Country {
        return Country(
            id = model.id ?: -1,
            name = model.name.orEmpty(),
            code = model.code.orEmpty(),
            visible = model.visible ?: false,
            flag = model.flag.orEmpty(),
            nationality = model.nationality.orEmpty(),
            currency = model.currency.orEmpty(),
            phoneCode = model.phoneCode.orEmpty()
        )
    }

    override fun domainToEntity(model: Country): CountryEntity {
        return CountryEntity(
            id = model.id,
            name = model.name,
            code = model.code,
            visible = model.visible,
            flag = model.flag,
            nationality = model.nationality,
            currency = model.currency,
            phoneCode = model.phoneCode
        )
    }

    override fun entityToDomain(model: CountryEntity): Country {
        return Country(
            id = model.id,
            name = model.name,
            code = model.code,
            visible = model.visible,
            flag = model.flag,
            nationality = model.nationality,
            currency = model.currency,
            phoneCode = model.phoneCode
        )
    }
}