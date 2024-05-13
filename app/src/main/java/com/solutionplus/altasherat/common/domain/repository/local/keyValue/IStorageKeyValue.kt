package com.solutionplus.altasherat.common.domain.repository.local.keyValue

interface IStorageKeyValue {
    suspend fun <Model> saveEntry(key: IStorageKeyEnum, data: Model)
    suspend fun <Model> readEntry(key: IStorageKeyEnum, defaultValue: Model): Model
}