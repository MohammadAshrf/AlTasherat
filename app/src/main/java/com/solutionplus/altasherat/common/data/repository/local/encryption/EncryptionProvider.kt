package com.solutionplus.altasherat.common.data.repository.local.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptionProvider : IEncryptionProvider {

    private val keyStore = KeyStore.getInstance(PROVIDER).apply {
        load(null)
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setKeySize(KEY_SIZE)
                    .build()
            )
        }.generateKey()
    }


    private val cipher = Cipher.getInstance(TRANSFORMATION)


    override fun encryptData(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val initialVector = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return initialVector + encrypted
    }

    override fun decryptData(bytes: ByteArray): ByteArray? {
        val initialVector = bytes.copyOfRange(0, cipher.blockSize)
        val encryptedData = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(initialVector))
        return cipher.doFinal(encryptedData)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
        private const val PROVIDER = "AndroidKeyStore"
        private const val ALIAS = "secret_key"
        private const val KEY_SIZE = 128
    }
}