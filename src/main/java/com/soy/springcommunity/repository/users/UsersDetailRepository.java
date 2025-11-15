package com.soy.springcommunity.repository.users;

import com.soy.springcommunity.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDetailRepository extends JpaRepository<UserDetail, Long> {

}
