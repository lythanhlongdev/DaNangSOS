package com.capstone2.dnsos.utils;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.HistoryMedia;
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

    public static boolean checkSize(MultipartFile file, long maxSize) {
        return (file.getSize() > maxSize * 1024 * 1024);
    }

    public static boolean checkFileTypeStart(@NotNull MultipartFile file, String[] listType) {
        return checkFileTypeStart(Objects.requireNonNull(file.getContentType()), listType);
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

    public static String getTypeFile(String file, String[] listType) {
        for (String type : listType) {
            if (file.endsWith(type)) {
                return type;
            }
        }
        return "File not supported";
    }

    public static HistoryMedia saveImgAndAudio(@NotNull List<MultipartFile> files, HistoryMedia historyMedia) throws Exception {
        if (files.isEmpty() || historyMedia == null) {
            throw new NotFoundException("List file empty and object history is null");
        }

        final String[] fileType = {".mp3", ".png", ".jpg"};
//        HistoryMedia historyMedia = HistoryMedia.builder().history(history).build();
        int indexImg = 0;
        for (int i = 0; i < Math.min(files.size(), 4); i++) {
            MultipartFile file = files.get(i);
            if (checkSize(file, 10)) {
                throw new Exception("File too large! Max size is 10MB");
            }

            String fileName = getString(file);
            String uniqueFile = historyMedia.getHistory().getHistoryId() + "-" + UUID.randomUUID() + "-" + fileName;

            Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
            Files.createDirectories(uploadDir);

            Path destination = uploadDir.resolve(uniqueFile);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String type = FileUtil.getTypeFile(uniqueFile, fileType);
            switch (type) {
                case ".mp3":
                    historyMedia.setVoice(uniqueFile);
                    break;
                case ".png":
                case ".jpg":
                    switch (indexImg) {
                        case 0:
                            historyMedia.setImage1(uniqueFile);
                            indexImg++;
                            break;
                        case 1:
                            historyMedia.setImage2(uniqueFile);
                            indexImg++;
                            break;
                        case 2:
                            historyMedia.setImage3(uniqueFile);
                            break;
                    }
                    break;
            }
        }

        return historyMedia;
    }

    private static String getString(MultipartFile file) throws Exception {
        String contentType = file.getContentType().toLowerCase();
        if (!checkFileTypeStart(contentType, LIST_FILE_TYPE)) {
            throw new Exception("File must be an image or an audio file (MP3)");
        }

        String fileName = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (contentType.startsWith("audio/")) {
            fileName = "voice." + fileName;
        } else if (contentType.startsWith("image/")) {
            fileName = "image." + fileName;
        } else {
            throw new InvalidParameterException("saveImgAndAudio(): Files are not supported: " + fileName);
        }

        return fileName;
    }

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
