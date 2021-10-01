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

import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors

class CustomTokenResponseConverter : Converter<Map<String?, String?>?, OAuth2AccessTokenResponse?> {

    override fun convert(tokenResponseParameters: Map<String?, String?>): OAuth2AccessTokenResponse {

        // Do some custom processing to decrypt the ID token
        var idToken = tokenResponseParameters["id_token"]
        if (idToken != null) {
            idToken = JweDecryptor().decrypt(idToken);
        }

        // We then need to resupply all standard parameters
        var scopes: Set<String?> = emptySet<String>()
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            val scope = tokenResponseParameters[OAuth2ParameterNames.SCOPE]
            scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).collect(Collectors.toSet())
        }
        val accessToken = tokenResponseParameters[OAuth2ParameterNames.ACCESS_TOKEN]
        val refreshToken = tokenResponseParameters[OAuth2ParameterNames.REFRESH_TOKEN]
        val expiresIn = java.lang.Long.valueOf(tokenResponseParameters[OAuth2ParameterNames.EXPIRES_IN])
        val accessTokenType = OAuth2AccessToken.TokenType.BEARER

        val extraParams = mapOf("id_token" to idToken)
        return OAuth2AccessTokenResponse
            .withToken(accessToken)
            .refreshToken(refreshToken)
            .scopes(scopes)
            .tokenType(accessTokenType)
            .expiresIn(expiresIn)
            .additionalParameters(extraParams)
            .build()
    }
}