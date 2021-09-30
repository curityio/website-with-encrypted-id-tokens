package com.curity.io

import org.jose4j.jwe.JsonWebEncryption
import java.security.PrivateKey


/*
 * Use the JOSE library to decrypt a JWT using JSON web encryption
 */
class JweDecryptor {

    fun decrypt(encryptedJwt: String): String {

        /*
        val key: PrivateKey = keyStore.getKeyOrThrow(keyId)
        val jwe = JsonWebEncryption()
        jwe.key = key
        jwe.contentEncryptionKey = key.encoded
        jwe.compactSerialization = encryptedJwt
        return jwe.plaintextString*/

        return encryptedJwt
    }
}
