package yte.intern.application.usecases.manageevents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import yte.intern.application.usecases.manageevents.entity.Users;
import yte.intern.application.usecases.manageevents.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    @Override
    public void createUser(UserDetails userDetails) {
        Users users = (Users) userDetails;
        users.setPassword(encoder.encode(users.getPassword()));
        usersRepository.save(users);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        Users oldUser = (Users) loadUserByUsername(userDetails.getUsername());
        Users newUser = (Users) userDetails;
        newUser.setId(oldUser.getId());
        usersRepository.save(newUser);
    }

    @Override
    public void deleteUser(String username) {
        usersRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        Users user = (Users) loadUserByUsername(username);
        if(encoder.matches(oldPassword,user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            usersRepository.save(user);
        }
        else{
            throw new BadCredentialsException("Wrong old password.");
        }

    }

    @Override
    public boolean userExists(String username) {
        return usersRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username);
    }
}
