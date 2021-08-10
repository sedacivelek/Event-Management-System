package yte.intern.application.usecases.manageevents.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private List<String> authorities;
}
