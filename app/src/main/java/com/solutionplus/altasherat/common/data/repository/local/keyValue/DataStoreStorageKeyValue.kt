package com.solutionplus.altasherat.common.data.repository.local.keyValue

import com.solutionplus.altasherat.common.domain.repository.local.keyValue.IStorageKeyEnum
import com.solutionplus.altasherat.common.domain.repository.local.keyValue.IStorageKeyValue
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.data.models.exception.SolutionXException
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DataStoreStorageKeyValue(private val context: Context) : IStorageKeyValue {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fileName")

    override suspend fun <Model> saveEntry(key: IStorageKeyEnum, data: Model) {
        context.dataStore.edit {
            when (data) {
                is String -> it[stringPreferencesKey(key.keyValue)] = data
                is Int -> it[intPreferencesKey(key.keyValue)] = data
                is Boolean -> it[booleanPreferencesKey(key.keyValue)] = data
                is Float -> it[floatPreferencesKey(key.keyValue)] = data
                is Long -> it[longPreferencesKey(key.keyValue)] = data
                else -> throw SolutionXException.Local.IOOperation(R.string.unsupported_type)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <Model> readEntry(key: IStorageKeyEnum, defaultValue: Model): Model {
        return when (defaultValue) {
            is String -> (context.dataStore.data.map { it[stringPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as Model

            is Int -> (context.dataStore.data.map { it[intPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as Model

            is Boolean -> (context.dataStore.data.map { it[booleanPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as Model

            is Float -> (context.dataStore.data.map { it[floatPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as Model

            is Long -> (context.dataStore.data.map { it[longPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as Model

            else -> throw SolutionXException.Local.IOOperation(R.string.unsupported_type)
        }
    }
}