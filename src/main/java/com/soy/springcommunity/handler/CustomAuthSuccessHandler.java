package com.soy.springcommunity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soy.springcommunity.dto.UsersSignInResponse;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        request.getSession(true);
        request.getSession().setMaxInactiveInterval(3600);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Users user = (Users) userDetails.getUser();

        UsersSignInResponse usersSignInResponse = new UsersSignInResponse(
                user.getId(),
                user.getNickname(),
                user.getFilesUserProfileImgUrl().getImgUrl()
        );

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        String json = this.mapper.writeValueAsString(usersSignInResponse);
        response.getWriter().write(json);




    }
}
