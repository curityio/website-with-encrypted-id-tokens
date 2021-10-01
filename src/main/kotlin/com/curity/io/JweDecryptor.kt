/*
 *  Copyright 2021 Curity AB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
