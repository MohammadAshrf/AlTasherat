package com.solutionplus.altasherat.features.signup.data.model.request

import android.text.TextUtils
import android.util.Patterns
import com.google.gson.annotations.SerializedName
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.features.signup.data.model.request.Phone

data class SignupRequest(
    @SerializedName("firstname")
    val firstName: String?= null,
    @SerializedName("lastname")
    val lastName: String?= null,
    @SerializedName("email")
    val email: String?= null,
    @SerializedName("phone")
    val phone : Phone?= null,
    @SerializedName("password")
    val password : String?= null,
    @SerializedName("password_confirmation")
    val passwordConfirmation : String?= null,
    @SerializedName("country")
    val countryId : Int?= null,
    @SerializedName("country_code")
    val countryCode: String?= null,
){

    fun validateFirstName():Boolean {
        return !(firstName!!.isBlank() || firstName.length < 3 || firstName.length > 15)
    }
    fun validateLastName():Boolean {
        return !(lastName!!.isBlank() || lastName.length < 3 || lastName.length > 15)
    }

    fun validatePassword():Boolean {
        return !(password!!.isBlank() ||password.length < 8 || password.length > 50)
    }

    fun validatePhone():Boolean {
        return phone!!.validatePhone()
    }

}
