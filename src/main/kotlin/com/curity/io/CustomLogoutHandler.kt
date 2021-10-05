package com.curity.io

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*
 * Use a custom logout handler that does not send an unencrypted ID token and reveal PII
 */
class CustomLogoutHandler : AbstractAuthenticationTargetUrlRequestHandler(), LogoutSuccessHandler {

    override fun determineTargetUrl(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ): String? {

        val endSessionEndpoint = "http://localhost:8443/oauth/v2/oauth-session/logout"
        val clientId = "website-client"
        val postLogoutRedirectUri = URLEncoder.encode("http://localhost:8080/", "UTF-8")
        return "${endSessionEndpoint}?client_id=${clientId}&post_logout_redirect_uri=${postLogoutRedirectUri}"
    }

    override fun onLogoutSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        super.handle(request, response, authentication)
    }
}