package com.capstone2.dnsos.utils;

import com.capstone2.dnsos.exceptions.DataNotFoundException;
import com.capstone2.dnsos.models.History;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FileUtil {

    private static final String[] LIST_FILE_TYPE = {"image/", "audio/"};

    public static String saveImgAndMp3(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // add first UUID in file name  => unique file
        String uniqueFile = UUID.randomUUID() + "_" + fileName;
        // url folder save file
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./templates/uploads");// get quyền để tạo fol
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
    public static boolean checkSize(MultipartFile file, long maxSize) {
        return (file.getSize() > maxSize * 1024 * 1024);
    }

    public static boolean checkFileTypeStart(@NotNull MultipartFile file, String[] listType) {
        for (String type : listType) {
            if (file.getContentType().startsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkFileTypeStart(@NotNull String contentType, String[] listType) {
        for (String type : listType) {
            if (contentType.startsWith(type)) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkFileTypeEnd(@NotNull String contentType, String[] listType) {
        for (String type : listType) {
            if (contentType.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    public static String getTypeFile( String file, String[] listType) {
        for (String type : listType) {
            if (file.endsWith(type)) {
                return type;
            }
        }
        return "File not supported";
    }

//    public static String renameFile(@NotNull @NotEmpty MultipartFile file, @Length(min = 1) String[] acceptanceCriteria) {
//        for (String criteria:acceptanceCriteria) {
//
//        }
//    }

    public static String[] saveImgAndAudio(@NotNull List<MultipartFile> files, History history) throws Exception {
        String[] listFileName = {"", "", "", ""};
        int i = 0;
        if (files.isEmpty() || history == null) {
            throw new DataNotFoundException("List file empty and object history is null");
        }
        for (MultipartFile file : files) {
            if (checkSize(file, 10)) {
                throw new Exception("File too large! Max size is 10MB");
            }

            String contentType = Objects.requireNonNull(file.getContentType()).toLowerCase();
            if (!checkFileTypeStart(contentType, LIST_FILE_TYPE)) {
                throw new Exception("File must be an image or an audio file (MP3)");
            }

            // Rename and save folder
            String fileName = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (contentType.startsWith("audio/")) {
                fileName = "voice." + fileName;
            } else if (contentType.startsWith("image/")) {
                fileName = "image." + fileName;
            } else {
                throw new InvalidParameterException("saveImgAndAudio(): Files are not supported: " + fileName);
            }

            // add first UUID in file name  => unique file name include  historyId-rescueStationId-userId-UUI-fileName
            String uniqueFile = history.getHistoryId() + "-" + history.getRescueStation().getRescueStationsId() + "-" + history.getUser().getUserId() + "-" + UUID.randomUUID() + "-" + fileName;
            // url folder save file
            Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");// get quyền để tạo fol
            // check folder
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // full url to file
            Path destination = Paths.get(uploadDir.toString(), uniqueFile);
            // copy file to folder
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            listFileName[i++] = uniqueFile;
        }
        return listFileName;
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
