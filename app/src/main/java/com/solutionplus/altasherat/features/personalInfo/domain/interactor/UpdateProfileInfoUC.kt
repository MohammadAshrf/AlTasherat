package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateProfileInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class UpdateProfileInfoUC(private val repository: IUpdateProfileRepository, private val saveUserUC: SaveUserUC) :
    BaseUseCase<UpdateUser, UpdateProfileInfoRequest>() {
    override suspend fun execute(params: UpdateProfileInfoRequest?): UpdateUser {
        requireNotNull(params) {
            throw LeonException.Local.RequestValidation(clazz = UpdateProfileInfoRequest::class)
        }

        validateRequest(params)?.let { message ->
            throw LeonException.Local.RequestValidation(
                clazz = UpdateProfileInfoRequest::class,
                message = message
            )
        }

        val result = repository.updateProfileInfo(params.remoteMap)
        saveUserUC.execute(result.user)
        return result
    }

    private fun validateRequest(request: UpdateProfileInfoRequest): String? {
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