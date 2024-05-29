package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.personalInfo.data.models.dto.ImageDto
import com.solutionplus.altasherat.features.personalInfo.domain.models.Image

internal object ImageMapper : Mapper<ImageDto, Image, Unit>() {
    override fun dtoToDomain(model: ImageDto): Image {
        return Image(
            id = model.id ?: -1,
            type = model.type.orEmpty(),
            createdAt = model.createdAt.orEmpty(),
            description = model.description.orEmpty(),
            updatedAt = model.updatedAt.orEmpty(),
            main = model.main ?: false,
            title = model.title.orEmpty(),
            path = model.path.orEmpty(),
            priority = model.priority ?: -1
        )
    }
}