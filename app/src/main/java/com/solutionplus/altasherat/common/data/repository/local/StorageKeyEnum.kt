package com.solutionplus.altasherat.common.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    ACCESS_TOKEN("accessToken"),
    USER("user"),
    COUNTRY_NAME("country_name"),
    COUNTRIES("countries"),
    IS_USER_LOGGED_IN("is_user_logged_in"),

}