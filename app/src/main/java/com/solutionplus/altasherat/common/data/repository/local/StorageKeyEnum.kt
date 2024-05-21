package com.solutionplus.altasherat.common.data.repository.local

import com.solutionplus.altasherat.common.domain.repository.local.IStorageKeyEnum

enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    ACCESS_TOKEN("accessToken"),
    USER("user"),
    ON_BOARDING_SHOWN("on_boarding_shown"),
    COUNTRY_NAME("country_name")
}