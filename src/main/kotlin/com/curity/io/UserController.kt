package com.curity.io

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/user")
    fun user(
        model: Model,
        @AuthenticationPrincipal oidcUser: OidcUser
    ): String {
        model.addAttribute("userName", oidcUser.name)
        return "user"
    }
}
