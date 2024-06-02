package com.solutionplus.altasherat.common.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    ACCESS_TOKEN("accessToken"),
    USER("user"),
    UPDATED_USER("updated_user"),
    IS_USER_LOGGED_IN("is_user_logged_in"),
    ONBOARDING_SHOWN("onboardingShown"),
    COUNTRIES("countries"),
    SELECTED_COUNTRY("selectedCountry")
}