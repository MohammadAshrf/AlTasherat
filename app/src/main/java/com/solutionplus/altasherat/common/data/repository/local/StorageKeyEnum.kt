package com.solutionplus.altasherat.common.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    ACCESS_TOKEN("accessToken"),
    USER("user"),
    COUNTRY_NAME("country_name"),
    COUNTRIES("countries"),
    IS_USER_LOGGED_IN("is_user_logged_in"),
    ON_BOARDING_SHOWN("onBoardingShown"),
    ARABIC_COUNTRIES("ar_countries"),
    ENGLISH_COUNTRIES("en_countries")
}