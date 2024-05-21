package com.solutionplus.altasherat.common.domain.repository.local

import java.lang.reflect.Type

interface IKeyValueStorageProvider {
    suspend fun <Model> saveEntry(key: IStorageKeyEnum, value: Model, type: Type)
    suspend fun <Model> getEntry(key: IStorageKeyEnum, defaultValue: Model, type: Type): Model?
    suspend fun <Model> updateEntry(key: IStorageKeyEnum, value: Model, type: Type)
    suspend fun <Model> deleteEntry(key: IStorageKeyEnum, type: Type)
    suspend fun <Model> hasKey(key: IStorageKeyEnum, value: Model): Boolean
}