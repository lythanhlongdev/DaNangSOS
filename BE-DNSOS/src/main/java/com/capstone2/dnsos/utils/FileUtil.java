package com.capstone2.dnsos.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {


    public static String saveImgAndMp3(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // add first UUID in file name  => unique file
        String uniqueFile = UUID.randomUUID() + "_" + fileName;
        // url folder save file
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./templates/uploads");
        // check folder
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // full url to file
        Path destination = Paths.get(uploadDir.toString(), uniqueFile);
        // copy file to folder
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFile;
    }



//    public static void saveImage(String uploadDir, String fileName, MultipartFile file) {
//        Path uploadPath = Path.of(uploadDir);
//        try (InputStream inputStream = file.getInputStream()) {
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            log.error("FileUtils.java Exception", e);
//            throw new FileException("Could not save image " + fileName);
//        }
//    }
}
