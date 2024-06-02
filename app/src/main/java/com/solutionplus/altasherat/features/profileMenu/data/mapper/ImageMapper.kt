package com.solutionplus.altasherat.features.profileMenu.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.profileMenu.data.model.entity.ImageEntity
import com.solutionplus.altasherat.features.profileMenu.domain.model.Image


internal object ImageMapper : Mapper<Unit, Image, ImageEntity>() {

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

    override fun dtoToDomain(model: Unit): Image {
        return Image(
            id = -1,
            type = "",
            createdAt = "",
            description = "",
            updatedAt = "",
            main = false,
            title = "",
            path = "",
            priority = -1
        )
    }
}