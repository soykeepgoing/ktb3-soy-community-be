package com.soy.springcommunity.repository.topics;

import com.soy.springcommunity.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
    Optional<Topics> findByCode(String code);
}
