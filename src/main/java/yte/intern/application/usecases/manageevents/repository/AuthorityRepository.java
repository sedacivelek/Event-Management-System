package yte.intern.application.usecases.manageevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.application.usecases.manageevents.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
