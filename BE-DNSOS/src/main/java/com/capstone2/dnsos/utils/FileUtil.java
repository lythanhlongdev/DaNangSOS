package com.capstone2.dnsos.utils;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.HistoryMedia;
import jakarta.validation.constraints.NotNull;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FileUtil {

    private static final String[] LIST_FILE_TYPE = {"image/", "audio/"};

    private static final String[] FILE_EXTENSION = {".mp3", ".m3a", ".png", ".jpg", ".jpeg"};

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

    public static String getTypeFile(String file, Set<String> listType) {
        for (String type : listType) {
            if (file.endsWith(type)) {
                return type;
            }
        }
        return "File not supported";
    }


    public static MediaType getMediaType(Resource resource) throws Exception {
        // Lấy đuôi mở rộng của tập tin để xác định kiểu nội dung
        String contentType = "application/octet-stream"; // Mặc định là kiểu dữ liệu nhị phân
        contentType = resource.getFile().toURI().toURL().openConnection().getContentType();
        return MediaType.parseMediaType(contentType);
    }

    private static String getContentTypeAvatar(@NotNull MultipartFile file) throws Exception {
        String contentType = file.getContentType();

        if (contentType == null || !contentType.toLowerCase().startsWith("image/")) {
            throw new Exception("File must be an image");
        }

        String fileName = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileName == null || !fileName.toLowerCase().matches("png|jpg|jpeg")) {
            throw new InvalidParameterException("File extension is not supported");
        }
        return "avatar." + fileName;
    }

    private final static String URL_AVATAR_USER = "./avatar/users/";
    private final static String URL_AVATAR_RESCUE_STATION = "./avatar/rescue_stations/";

    public static String saveAvatar(@NotNull MultipartFile avatar, Long userId, int checkRole) throws Exception {
        if (avatar.isEmpty() || userId == null) {
            throw new NotFoundException("Avatar is empty or userId is null");
        }

        if (checkSize(avatar, 5)) {
            throw new Exception("Avatar too large! Max size is 5MB");
        }

        String uniqueFileName = String.format("%s-%s-%s", userId, LocalDateTime.now(), getContentTypeAvatar(avatar));
        Path uploadDir = null;
        if (checkRole == 1) {
            uploadDir = Paths.get(System.getProperty("user.dir"), URL_AVATAR_USER);
        } else if (checkRole == 2) {
            uploadDir = Paths.get(System.getProperty("user.dir"), URL_AVATAR_RESCUE_STATION);
        } else {
            throw new InvalidParameterException("Error: FileUtil.saveAvatar(), The input parameter is not 1 or 2 => " + checkRole);
        }
        Files.createDirectories(uploadDir);
        Path destination = uploadDir.resolve(uniqueFileName);
        Files.copy(avatar.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public static HistoryMedia saveImgAndAudio(HistoryMedia historyMedia, MultipartFile img1, MultipartFile img2, MultipartFile img3, MultipartFile voice) throws Exception {
        if (historyMedia == null) {
            throw new NotFoundException("Object history is null");
        }

        final Set<String> SUPPORTED_FILE_TYPES = new HashSet<>(Arrays.asList(".mp3", ".m3a", ".png", ".jpg", "jpeg"));
        final int MAX_SIZE_MB = 10;

        if (img1 != null && !img1.isEmpty()) {
            String imgName1 = saveImage(historyMedia, img1, SUPPORTED_FILE_TYPES);
            historyMedia.setImage1(imgName1);
        }
        if (img2 != null && !img2.isEmpty()) {
            String imgName2 = saveImage(historyMedia, img2, SUPPORTED_FILE_TYPES);
            historyMedia.setImage2(imgName2);
        }
        if (img3 != null && !img3.isEmpty()) {
            String imgName3 = saveImage(historyMedia, img3, SUPPORTED_FILE_TYPES);
            historyMedia.setImage3(imgName3);
        }
        if (voice != null && !voice.isEmpty()) {
            String voiceName = saveVoice(historyMedia, voice, SUPPORTED_FILE_TYPES);
            historyMedia.setVoice(voiceName);
        }

        return historyMedia;
    }

    private static String saveImage(HistoryMedia historyMedia, MultipartFile file, Set<String> supportedFileTypes) throws Exception {
        if (checkSize(file, 10)) {
            throw new Exception("Image file too large! Max size is 10MB");
        }

        String fileName = getString(file);
        String uniqueFile = historyMedia.getHistory().getId() + "-" + UUID.randomUUID() + "-" + fileName;

        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
        Files.createDirectories(uploadDir);

        Path destination = uploadDir.resolve(uniqueFile);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        String type = FileUtil.getTypeFile(uniqueFile, supportedFileTypes);
        if (!type.equals(".png") && !type.equals(".jpg") && !type.equals(".jpeg") && !type.equals("png") && !type.equals("jpg") && !type.equals("jpeg")) {
            throw new Exception("Chỉ nhận dạng hình ảnh có đuôi mở rộng: .png, .jpg, .jpeg");
        }
        return uniqueFile;
    }

//    private static void saveImage(HistoryMedia historyMedia, MultipartFile file, String field, Set<String> supportedFileTypes) throws Exception {
//        if (checkSize(file, 10)) {
//            throw new Exception("Image file too large! Max size is 10MB");
//        }
//
//        String fileName = getString(file);
//        String uniqueFile = historyMedia.getHistory().getId() + "-" + UUID.randomUUID() + "-" + fileName;
//
//        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
//        Files.createDirectories(uploadDir);
//
//        Path destination = uploadDir.resolve(uniqueFile);
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//
//        String type = FileUtil.getTypeFile(uniqueFile, supportedFileTypes);
//        if (type.equals(".png") || type.equals(".jpg") || type.equals(".jpeg")) {
//            try {
//                Field declaredField = HistoryMedia.class.getDeclaredField(field);
//                declaredField.setAccessible(true);
//                declaredField.set(historyMedia, uniqueFile);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//                throw new Exception("Failed to update file field in HistoryMedia object.");
//            }
//        }
//    }

//    private static void saveVoice(HistoryMedia historyMedia, MultipartFile file, String field, Set<String> supportedFileTypes) throws Exception {
//        if (checkSize(file, 10)) {
//            throw new Exception("Voice file too large! Max size is 10MB");
//        }
//
//        String fileName = getString(file);
//        String uniqueFile = historyMedia.getHistory().getId() + "-" + UUID.randomUUID() + "-" + fileName;
//
//        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
//        Files.createDirectories(uploadDir);
//
//        Path destination = uploadDir.resolve(uniqueFile);
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//
//        String type = FileUtil.getTypeFile(uniqueFile, supportedFileTypes);
//        if (type.equals(".mp3") || type.equals(".m3a")) {
//            try {
//                Field declaredField = HistoryMedia.class.getDeclaredField(field);
//                declaredField.setAccessible(true);
//                declaredField.set(historyMedia, uniqueFile);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//                throw new Exception("Failed to update file field in HistoryMedia object.");
//            }
//        }
//    }

    private static String saveVoice(HistoryMedia historyMedia, MultipartFile file, Set<String> supportedFileTypes) throws Exception {
        if (checkSize(file, 10)) {
            throw new Exception("Voice file too large! Max size is 10MB");
        }

        String fileName = getString(file);
        String uniqueFile = historyMedia.getHistory().getId() + "-" + UUID.randomUUID() + "-" + fileName;

        Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
        Files.createDirectories(uploadDir);

        Path destination = uploadDir.resolve(uniqueFile);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        String type = FileUtil.getTypeFile(uniqueFile, supportedFileTypes);
        if (!type.equals(".mp3") && !type.equals(".m3a")) {
            throw new Exception("Voice chỉ nhận dạng 2 loại: .mp3 và .m3a ");
        }
        return uniqueFile;
    }

    public static HistoryMedia saveImgAndAudio(@NotNull List<MultipartFile> files, HistoryMedia historyMedia) throws Exception {
        if (files.isEmpty() || historyMedia == null) {
            throw new NotFoundException("List file empty and object history is null");
        }

        final String[] fileType = {".mp3", ".m3a", ".png", ".jpg", "jpeg"};
//        HistoryMedia historyMedia = HistoryMedia.builder().history(history).build();
        int indexImg = 0;
        for (int i = 0; i < Math.min(files.size(), 4); i++) {
            MultipartFile file = files.get(i);
            if (checkSize(file, 10)) {
                throw new Exception("File too large! Max size is 10MB");
            }

            String fileName = getString(file);
            String uniqueFile = historyMedia.getHistory().getId() + "-" + UUID.randomUUID() + "-" + fileName;

            Path uploadDir = Paths.get(System.getProperty("user.dir"), "./uploads");
            Files.createDirectories(uploadDir);

            Path destination = uploadDir.resolve(uniqueFile);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String type = FileUtil.getTypeFile(uniqueFile, fileType);
            if (FILE_EXTENSION[0].equals(type) || FILE_EXTENSION[1].equals(type)) {
                historyMedia.setVoice(uniqueFile);
            }
            switch (type) {
//                case ".mp3":
//                case ".m3a":
//                    historyMedia.setVoice(uniqueFile);
//                    break;

                case ".png":
                case ".jpg":
                case ".jpeg":
                    switch (indexImg) {
                        case 0:
                            if (historyMedia.getImage1().isEmpty()) {
                                historyMedia.setImage1(uniqueFile);
                            }
                            indexImg++;
                            break;
                        case 1:
                            if (historyMedia.getImage2().isEmpty()) {
                                historyMedia.setImage2(uniqueFile);
                            }
                            indexImg++;

                            break;
                        case 2:
                            if (historyMedia.getImage3().isEmpty()) {
                                historyMedia.setImage3(uniqueFile);
                            }
                            indexImg = 0;
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


    // Method to read KilometerMin objects from a file

    // Method to read KilometerMin objects from a file
    public static List<KilometerMin> readFromFile(String directoryPath, String filename) {
        List<KilometerMin> kilometers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(directoryPath, filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Long rescueStationID = Long.parseLong(parts[0]);
                    String rescueStationName = parts[1];
                    Double kilometer = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    kilometers.add(new KilometerMin(rescueStationID, rescueStationName, kilometer, count));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return kilometers;
    }

    // Method to write KilometerMin objects to a file
    public static void writeToFile(String directoryPath, List<KilometerMin> kilometers, String filename) {
        File directory = new File(directoryPath);

        // Check if the directory exists, if not, create it
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (KilometerMin km : kilometers) {
                writer.write(km.getRescueStationID() + "," + km.getRescueStationName() + "," + km.getKilometers() + "," + km.getCount());
                writer.newLine();
            }
            System.out.println("Data has been written to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
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
