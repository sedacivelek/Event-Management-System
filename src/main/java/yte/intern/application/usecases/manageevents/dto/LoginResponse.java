package yte.intern.application.usecases.manageevents.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
