package com.solutionplus.altasherat.features.signup.domain.usecase

import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.countries.country.Country
import com.solutionplus.altasherat.features.signup.data.model.request.SignupRequest
import com.solutionplus.altasherat.features.signup.domain.model.User
import com.solutionplus.altasherat.features.signup.domain.repository.ISignupRepository
import javax.inject.Inject

class SignupUC @Inject constructor(
    private val repository: ISignupRepository,
) : BaseUseCase<User, SignupRequest>() {

//    suspend fun getCountries(): List<Country> {
//        return repository.getCountries()
//    }

    public override suspend fun execute(params: SignupRequest?): User {
        val result = repository.signupWithPhone(params!!)
        repository.saveUser(result.user)
        repository.saveAccessToken(result.token)
        repository.getUser()
        return result.user
    }

}