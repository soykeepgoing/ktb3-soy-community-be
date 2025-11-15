package com.soy.springcommunity.repository.files;

import com.soy.springcommunity.entity.FilesUserProfileImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesUserProfileImgRepository extends JpaRepository<FilesUserProfileImgUrl, Long> {}