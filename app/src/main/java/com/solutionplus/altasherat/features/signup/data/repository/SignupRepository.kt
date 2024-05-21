package com.solutionplus.altasherat.features.signup.data.repository


import com.solutionplus.altasherat.features.signup.data.model.entity.UserEntity
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.data.mapper.SignupMapper
import com.solutionplus.altasherat.features.signup.data.mapper.UserMapper
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import com.solutionplus.altasherat.features.signup.domain.repository.local.ISignupLocalDS
import com.solutionplus.altasherat.features.signup.domain.model.Signup
import com.solutionplus.altasherat.features.signup.domain.repository.remote.ISignupRemoteDS


internal class SignupRepository  (
    private val remoteDs: ISignupRemoteDS,
    private val localDs: ISignupLocalDS,
) : ISignupRepository {

    override suspend fun signupWithPhone(signupRequest: SignupRequest): Signup {
        val result = remoteDs.signupWithPhone(signupRequest)
        return SignupMapper.dtoToDomain(result!!)
    }

//    override suspend fun getCountries(): List<Country>  {
//       return  remoteDs.getCountries()
//    }

    override suspend fun saveUser(user: User) {
        val result = UserMapper.domainToEntity(user)
        localDs.saveUser(result)
    }

    override suspend fun saveAccessToken(token: String) =
        localDs.saveAccessToken(token)


    override suspend fun getUser(): UserEntity =
        localDs.getUser()



}