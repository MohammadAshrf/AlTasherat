package com.solutionplus.altasherat.features.personalInfo.data.mappers

import com.solutionplus.altasherat.common.data.mapper.Mapper


import com.solutionplus.altasherat.features.profileMenu.data.model.entity.ImageEntity
/*

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

    override fun domainToEntity(model: Image): ImageEntity {
        return ImageEntity(
            id = model.id,
            type = model.type,
            createdAt = model.createdAt,
            description = model.description,
            updatedAt = model.updatedAt,
            main = model.main,
            title = model.title,
            path = model.path,
            priority = model.priority
        )
    }

    override fun entityToDomain(model: ImageEntity): Image {
        return Image(
            id = model.id,
            type = model.type,
            createdAt = model.createdAt,
            description = model.description,
            updatedAt = model.updatedAt,
            main = model.main,
            title = model.title,
            path = model.path,
            priority = model.priority
        )
    }
}*/
