package com.soy.springcommunity.repository.users;

import com.soy.springcommunity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailAndIsDeletedFalse(String email);
    Optional<Users> findByNickname(String nickname);
    Optional<Users> findByIdAndIsDeletedFalse(Long id);
    boolean existsById(Long id);
}
