package com.soy.springcommunity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soy.springcommunity.dto.UsersSignInResponse;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    protected void setSession(
            Authentication authentication,
            HttpServletRequest request
    ) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(3600);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        setSession(authentication, request);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Users user = (Users) userDetails.getUser();

        UsersSignInResponse usersSignInResponse = new UsersSignInResponse(
                user.getId(),
                user.getNickname(),
                user.getFilesUserProfileImgUrl().getImgUrl(),
                user.getRole().name()
        );

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        String json = this.mapper.writeValueAsString(usersSignInResponse);
        response.getWriter().write(json);
    }
}
