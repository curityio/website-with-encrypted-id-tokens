
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

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.util.Arrays

@Configuration
class OAuth2SecurityConfig : WebSecurityConfigurerAdapter() {

    /*
     * Configure overall behavior
     */
    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/error").permitAll()
            .anyRequest().authenticated()
            .and()
            .logout()
                .logoutSuccessHandler(CustomLogoutHandler())
            .and()
            .oauth2Login()
                .tokenEndpoint()
                    .accessTokenResponseClient(accessTokenResponseClient())
    }

    /*
     * Used to decrypt access tokens
     */
    @Bean
    fun accessTokenResponseClient(): OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest?>? {

        val accessTokenResponseClient = DefaultAuthorizationCodeTokenResponseClient()
        val tokenResponseHttpMessageConverter = OAuth2AccessTokenResponseHttpMessageConverter()
        tokenResponseHttpMessageConverter.setTokenResponseConverter(CustomTokenResponseConverter())
        val restTemplate = RestTemplate(
            Arrays.asList(
                FormHttpMessageConverter(), tokenResponseHttpMessageConverter
            )
        )

        restTemplate.errorHandler = OAuth2ErrorResponseErrorHandler()
        accessTokenResponseClient.setRestOperations(restTemplate)
        return accessTokenResponseClient
    }
}
