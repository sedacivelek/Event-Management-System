package yte.intern.application.usecases.manageevents.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yte.intern.application.usecases.manageevents.entity.Users;
import yte.intern.application.usecases.manageevents.service.CustomUserDetailsManager;
import yte.intern.application.usecases.manageevents.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private final CustomUserDetailsManager userDetailsManager;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization!=null && authorization.startsWith("Bearer")){
            String jwtToken = authorization.substring(7);
            String username = JwtUtil.extractUsername(jwtToken,secretKey);

            Users users = (Users) userDetailsManager.loadUserByUsername(username);
            var token = new UsernamePasswordAuthenticationToken(users.getUsername(),null,users.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
