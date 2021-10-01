package com.curity.io

import org.jose4j.jwe.JsonWebEncryption
import java.io.FileInputStream
import java.security.Key
import java.security.KeyStore

/*
 * Use the JOSE library to decrypt a JWT using JSON web encryption
 */
class JweDecryptor {

    fun decrypt(encryptedJwt: String): String {

        val privateKey = loadPrivateKey()

        val jwe = JsonWebEncryption()
        jwe.key = privateKey
        jwe.compactSerialization = encryptedJwt
        return jwe.plaintextString
    }

    /*
     * Load the private key used to decrypt the JWT
     */
    private fun loadPrivateKey(): Key {

        val keyFilePath = "./keys/client.p12"
        val privateKeyPassword = "Password1";

        FileInputStream(keyFilePath).use { fileStream ->

            val keystore = KeyStore.getInstance("PKCS12")
            keystore.load(fileStream, privateKeyPassword.toCharArray())
            return keystore.getKey(keystore.aliases().nextElement(), privateKeyPassword.toCharArray())
        }
    }
}
