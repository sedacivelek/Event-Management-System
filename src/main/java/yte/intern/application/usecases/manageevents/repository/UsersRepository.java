package yte.intern.application.usecases.manageevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.application.usecases.manageevents.entity.Users;

public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);

    void deleteByUsername(String username);
    boolean existsByUsername(String username);
}
