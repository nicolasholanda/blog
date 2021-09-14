package com.github.nicolasholanda.blog.api;

import com.github.nicolasholanda.blog.security.JwtUtil;
import com.github.nicolasholanda.blog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping(value = "/token")
    public void refreshToken(HttpServletResponse response,
                             @AuthenticationPrincipal UserDetailsImpl loggedUser) {
        String token = jwtUtil.generateToken(loggedUser.getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
