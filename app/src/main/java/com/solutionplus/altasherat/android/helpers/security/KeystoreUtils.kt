package com.example.solutionx.android.helpers.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeystoreUtils {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val AES_GCM_NOPADDING = "AES/GCM/NoPadding"

    private const val KEY_ALIAS = "my_key_alias"

    fun getSecretKey(): SecretKey {
        val keystore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keystore.load(null)

        if (!keystore.containsAlias(KEY_ALIAS)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false)
                    .setKeySize(128)
                    .build()
            )
            keyGenerator.generateKey()
        }

        return keystore.getKey(KEY_ALIAS, null) as SecretKey
    }

    fun encrypt(input: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Pair(iv, encrypted)
    }

    fun decrypt(iv: ByteArray, encrypted: ByteArray): String {
        val cipher = Cipher.getInstance(AES_GCM_NOPADDING)
        val spec = GCMParameterSpec(128, iv)  // AlgorithmParameterSpec
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        return String(cipher.doFinal(encrypted), Charsets.UTF_8)
    }
}