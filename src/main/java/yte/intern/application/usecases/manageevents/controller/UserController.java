package yte.intern.application.usecases.manageevents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.application.usecases.manageevents.dto.UserDTO;
import yte.intern.application.usecases.manageevents.service.UserService;
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/addUser")
    @PreAuthorize("permitAll()")
    public String addUser(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

}
