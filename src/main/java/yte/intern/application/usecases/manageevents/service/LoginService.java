package yte.intern.application.usecases.manageevents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import yte.intern.application.usecases.manageevents.dto.LoginRequest;
import yte.intern.application.usecases.manageevents.dto.LoginResponse;
import yte.intern.application.usecases.manageevents.util.JwtUtil;

@Service
@RequiredArgsConstructor
@PropertySource(value = {"classpath:application.properties"})
public class LoginService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    private final AuthenticationProvider authenticationProvider;


    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());

        try {
            Authentication user = authenticationProvider.authenticate(authentication);
            String token = JwtUtil.generateToken(user,secretKey,7);
            return new LoginResponse(token);
        }
        catch (AuthenticationException e){
            e.printStackTrace();
        }
    return null;

    }
}
