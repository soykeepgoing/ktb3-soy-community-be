package com.soy.springcommunity.service;

import com.soy.springcommunity.repository.files.FilesUserProfileImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.soy.springcommunity.utils.ConstantUtil.UPLOAD_USERS_DIR;

@Service
public class FilesService {
    private FilesUserProfileImgRepository userFilesRepository;

    @Autowired
    public FilesService(
            FilesUserProfileImgRepository userFilesRepository
    ){
        this.userFilesRepository = userFilesRepository;
    }

    public String saveFile(MultipartFile file) throws IOException {
        File uploadDir = new File(UPLOAD_USERS_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = UUID.randomUUID() + ".png";
        String filePath = UPLOAD_USERS_DIR + fileName;
        file.transferTo(new File(filePath));
        return "http://localhost:8080/uploads/" + fileName;
    }


}
