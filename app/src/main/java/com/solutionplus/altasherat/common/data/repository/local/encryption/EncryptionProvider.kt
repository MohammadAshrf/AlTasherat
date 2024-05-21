package com.solutionplus.altasherat.common.data.repository.local.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.solutionplus.altasherat.common.domain.repository.local.encryption.IEncryptionProvider
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptionProvider: IEncryptionProvider {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

     fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret_key", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret_key",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setKeySize(128)
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

    override fun decryptData(bytes: ByteArray): ByteArray {
        val initialVector = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(initialVector))
        return cipher.doFinal(data)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}