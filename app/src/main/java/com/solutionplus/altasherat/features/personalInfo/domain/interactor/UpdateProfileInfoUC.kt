package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateProfileInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository

class UpdateProfileInfoUC(private val repository: IUpdateProfileRepository) :
    BaseUseCase<UpdateUser, UpdateProfileInfoRequest>() {
    override suspend fun execute(params: UpdateProfileInfoRequest?): UpdateUser {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            throw LeonException.Local.RequestValidation(
                clazz = UpdateProfileInfoRequest::class,
                errors = errorMessages
            )
        }

        val result = repository.updateProfileInfo(params!!.remoteMap)
        repository.saveProfileInfo(result.user)
        return result
    }

    private fun UpdateProfileInfoRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!isFirstNameValid()) {
            errorKeys[Validation.FIRST_NAME] = R.string.invalid_first_name
        }
        if (!isMiddleNameValid()) {
            errorKeys[Validation.MIDDLE_NAME] = R.string.invalid_middle_name
        }
        if (!isLastNameValid()) {
            errorKeys[Validation.LAST_NAME] = R.string.invalid_last_name
        }
        if (!isEmailValid()) {
            errorKeys[Validation.EMAIL] = R.string.invalid_email
        }
        if (!isPhoneValid()) {
            errorKeys[Validation.PHONE] = R.string.invalid_phone
        }
        if (!isCountryValid()) {
            errorKeys[Validation.COUNTRY] = R.string.invalid_country
        }

        return errorKeys
    }
}