package com.soy.springcommunity.repository.files;

import com.soy.springcommunity.entity.FilesPostImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;

@Repository
public interface FilesPostImgRepository extends JpaRepository<FilesPostImgUrl, Long> {
}
