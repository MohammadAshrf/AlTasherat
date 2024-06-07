
package com.solutionplus.altasherat.features.login.data.mapper

import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.login.data.model.dto.LoginDto
import com.solutionplus.altasherat.features.login.data.model.entity.LoginEntity
import com.solutionplus.altasherat.features.login.domain.model.Login
import com.solutionplus.altasherat.features.services.user.data.mappers.UserMapper


internal object LoginMapper : Mapper<LoginDto, Login, LoginEntity>() {
    override fun dtoToDomain(model: LoginDto): Login {
        return Login(
            message = model.message.orEmpty(),
            accessToken = model.token.orEmpty(),
            userInfo = UserMapper.dtoToDomain(model.user!!)
        )
    }

    override fun domainToEntity(model: Login): LoginEntity {
        return LoginEntity(
            token = model.accessToken,
            user = UserMapper.domainToEntity(model.userInfo)
        )

    }


}
