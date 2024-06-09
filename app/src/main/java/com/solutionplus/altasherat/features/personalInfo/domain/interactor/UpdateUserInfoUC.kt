package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateUserInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateUserRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class UpdateUserInfoUC(private val repository: IUpdateUserRepository, private val saveUserUC: SaveUserUC
) :
    BaseUseCase<UpdateUser, UpdateUserInfoRequest>() {
    override suspend fun execute(params: UpdateUserInfoRequest?): UpdateUser {
        requireNotNull(params) {
            throw LeonException.Local.RequestValidation(
                clazz = UpdateUserInfoRequest::class,
                message = "Request is null"
            )
        }

        validateRequest(params)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = UpdateUserInfoRequest::class,
                message = message
            )
        }

        val result = repository.updateUserInfo(params)
          saveUserUC.execute(result.user)
//        repository.saveUpdatedUser(result.user)
        return result
    }

    private fun validateRequest(request: UpdateUserInfoRequest): String? {
        return request.run {
            when {
                !isFirstNameValid() -> "First name is not valid"
                !isMiddleNameValid() -> "Middle name is not valid"
                !isLastNameValid() -> "Last name is not valid"
                !isEmailValid() -> "Email is not valid"
                !isBirthdateValid() -> "Birthdate is not valid"
                !isCountryValid() -> "Country is not valid"
                else -> null
            }
        }
    }
}