package com.solutionplus.altasherat.common.domain.repository.local.encryption

interface IEncryptionProvider {
    fun encryptData(bytes: ByteArray): ByteArray
    fun decryptData(bytes: ByteArray): ByteArray
}