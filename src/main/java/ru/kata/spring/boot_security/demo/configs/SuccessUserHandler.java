package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        roles.forEach(role -> System.out.println(role));

        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("admin found");
            httpServletResponse.sendRedirect("/admin/");
        } else if (roles.contains("ROLE_USER")){
            System.out.println("user found");
            httpServletResponse.sendRedirect("/users/");
        } else {
            System.out.println("no one found");
            httpServletResponse.sendRedirect("/index");
        }
    }
}