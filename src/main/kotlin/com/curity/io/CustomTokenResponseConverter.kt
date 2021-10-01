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