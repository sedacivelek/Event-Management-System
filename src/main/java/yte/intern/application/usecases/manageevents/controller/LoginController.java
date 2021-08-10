package yte.intern.application.usecases.manageevents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.application.usecases.manageevents.dto.LoginRequest;
import yte.intern.application.usecases.manageevents.dto.LoginResponse;
import yte.intern.application.usecases.manageevents.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private String token;
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = loginService.login(loginRequest);
        token = loginResponse.getToken();
        return loginResponse;
    }
    @GetMapping("/login")
    public String success(){
        return token;
    }

}
