package com.solutionplus.altasherat.features.services.country.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountriesResponse
import com.solutionplus.altasherat.features.services.country.data.models.dto.CountryDto
import com.solutionplus.altasherat.features.services.country.data.models.entity.CountryEntity
import com.solutionplus.altasherat.features.services.country.domain.models.Country

internal object CountryMapper : Mapper<CountryDto, Country, CountryEntity>() {
    override fun dtoToDomain(model: CountryDto): Country {
        return Country(
            id = model.id ?: -1,
            name = model.name.orEmpty(),
            code = model.code.orEmpty(),
            flag = model.flag.orEmpty(),
            currency = model.currency.orEmpty(),
            phoneCode = model.phoneCode.orEmpty()
        )
    }

    override fun domainToEntity(model: Country): CountryEntity {
        return CountryEntity(
            id = model.id,
            name = model.name,
            code = model.code,
            flag = model.flag,
            currency = model.currency,
            phoneCode = model.phoneCode
        )
    }

    override fun entityToDomain(model: CountryEntity): Country {
        return Country(
            id = model.id,
            name = model.name,
            code = model.code,
            flag = model.flag,
            currency = model.currency,
            phoneCode = model.phoneCode
        )
    }


}