package com.solutionplus.altasherat.features.signup.data.mapper

import com.solutionplus.altasherat.features.signup.data.model.dto.SignupDto
import com.solutionplus.altasherat.features.signup.data.model.entity.SignupEntity
import com.solutionplus.altasherat.common.data.mapper.Mapper
import com.solutionplus.altasherat.features.signup.domain.model.Signup


internal object SignupMapper : Mapper<SignupDto, Signup, SignupEntity>() {
    override fun dtoToDomain(model: SignupDto): Signup {
        return Signup(
            message = model.message.orEmpty(),
            token = model.token.orEmpty(),
            user = UserMapper.dtoToDomain(model.user!!)
        )
    }

    override fun domainToEntity(model: Signup): SignupEntity {
        return SignupEntity(
            token = model.token,
            user = UserMapper.domainToEntity(model.user)
        )
    }


}