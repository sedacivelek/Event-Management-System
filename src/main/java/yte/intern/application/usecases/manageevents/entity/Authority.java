package yte.intern.application.usecases.manageevents.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    private String authority;
    @ManyToMany(mappedBy = "authorities")
    private Set<Users> users;
}
