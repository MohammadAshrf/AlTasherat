package com.solutionplus.altasherat.features.personalInfo.domain.interactor

import android.os.Build
import androidx.annotation.RequiresApi
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.constants.Validation
import com.solutionplus.altasherat.common.data.model.exception.LeonException
import com.solutionplus.altasherat.common.domain.interactor.BaseUseCase
import com.solutionplus.altasherat.features.personalInfo.data.models.request.UpdateProfileInfoRequest
import com.solutionplus.altasherat.features.personalInfo.domain.models.UpdateUser
import com.solutionplus.altasherat.features.personalInfo.domain.repository.IUpdateProfileRepository
import com.solutionplus.altasherat.features.services.user.domain.interactor.SaveUserUC

class UpdateProfileInfoUC(private val repository: IUpdateProfileRepository, private val saveUserUC: SaveUserUC) :
    BaseUseCase<UpdateUser, UpdateProfileInfoRequest>() {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun execute(params: UpdateProfileInfoRequest?): UpdateUser {
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
    @RequiresApi(Build.VERSION_CODES.O)
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
        if (!isBirthDateValid()){
            errorKeys[Validation.BIRTH_DATE] = R.string.invalid_birth_date
        }
        if (!isImageValid()){
            errorKeys[Validation.IMAGE] = R.string.invalid_image
        }

        return errorKeys
    }
}