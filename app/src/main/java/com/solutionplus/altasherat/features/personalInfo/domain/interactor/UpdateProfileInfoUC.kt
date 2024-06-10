package com.solutionplus.altasherat.features.personalInfo.domain.interactor


import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation.BIRTH_DATE
import com.solutionplus.altasherat.common.data.constants.Validation.COUNTRY
import com.solutionplus.altasherat.common.data.constants.Validation.EMAIL
import com.solutionplus.altasherat.common.data.constants.Validation.FIRST_NAME
import com.solutionplus.altasherat.common.data.constants.Validation.IMAGE
import com.solutionplus.altasherat.common.data.constants.Validation.LAST_NAME
import com.solutionplus.altasherat.common.data.constants.Validation.MIDDLE_NAME
import com.solutionplus.altasherat.common.data.constants.Validation.PHONE
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateProfileInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateProfileInfo
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class UpdateProfileInfoUC(
    private val repository: IUpdateProfileRepository,
    private val saveUserUC: SaveUserUC
) :
    BaseUseCase<UpdateProfileInfo, UpdateProfileInfoRequest>() {

    override suspend fun execute(params: UpdateProfileInfoRequest?): UpdateProfileInfo {
        val errorMessages = params?.validateRequest()
        if (!errorMessages.isNullOrEmpty()) {
            throw LeonException.Local.RequestValidation(
                clazz = UpdateProfileInfoRequest::class,
                errors = errorMessages
            )
        }

        val result = repository.updateProfileInfo(params!!.remoteMap)
        saveUserUC.execute(result.user)
        return result
    }

    private fun UpdateProfileInfoRequest.validateRequest(): Map<String, Int> {
        val errorKeys = mutableMapOf<String, Int>()

        if (!isFirstNameValid()) {
            errorKeys[FIRST_NAME] = R.string.invalid_first_name
        }
        if (!isMiddleNameValid()) {
            errorKeys[MIDDLE_NAME] = R.string.invalid_middle_name
        }
        if (!isLastNameValid()) {
            errorKeys[LAST_NAME] = R.string.invalid_last_name
        }
        if (!isEmailValid()) {
            errorKeys[EMAIL] = R.string.invalid_email
        }
        if (!isPhoneValid()) {
            errorKeys[PHONE] = R.string.invalid_phone
        }
        if (!isCountryValid()) {
            errorKeys[COUNTRY] = R.string.invalid_country
        }
        if (!isBirthDateValid()) {
            errorKeys[BIRTH_DATE] = R.string.invalid_birth_date
        }
        if (!isImageValid()) {
            errorKeys[IMAGE] = R.string.invalid_image
        }
        return errorKeys
    }
}