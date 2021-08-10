package yte.intern.application.usecases.manageevents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yte.intern.application.usecases.manageevents.dto.UserDTO;
import yte.intern.application.usecases.manageevents.entity.Authority;
import yte.intern.application.usecases.manageevents.entity.Users;
import yte.intern.application.usecases.manageevents.repository.AuthorityRepository;
import yte.intern.application.usecases.manageevents.repository.UsersRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public String addUser(UserDTO userDTO){
        Set<Authority> authorities = userDTO.getAuthorities()
                .stream().map(authority-> new Authority(null, authority,new HashSet<>()))
                .collect(Collectors.toSet());

        authorityRepository.saveAll(authorities);

        Users users = new Users(null,userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()),authorities,true,true,true,true);
        usersRepository.save(users);
        return "Sign up operation is successful";
    }
}
