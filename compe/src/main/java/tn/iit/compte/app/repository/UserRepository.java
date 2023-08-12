package tn.iit.compte.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.compte.app.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findUserByEmail(String email);
}
