package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository

class UpdateUserInfoUC(private val repository: IUpdateUserRepository) :
    BaseUseCase<UpdateUser, UpdateUserInfoRequest>() {
    override suspend fun execute(params: UpdateUserInfoRequest?): UpdateUser {
        requireNotNull(params) {
            throw LeonException.Local.RequestValidation(clazz = UpdateUserInfoRequest::class)
        }

        validateRequest(params)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = UpdateUserInfoRequest::class,
                message = message
            )
        }

        val result = repository.updateUserInfo(params.remoteMap)
        repository.saveUpdatedUser(result.user)
        return result
    }

    private fun validateRequest(request: UpdateUserInfoRequest): String? {
        return request.run {
            when {
                !isFirstNameValid() -> "First name is not valid"
                !isMiddleNameValid() -> "Middle name is not valid"
                !isLastNameValid() -> "Last name is not valid"
                !isEmailValid() -> "Email is not valid"
                !isPhoneValid() -> "Phone number is not valid"
                !isCountryValid() -> "Country is not valid"
                else -> null
            }
        }
    }
}