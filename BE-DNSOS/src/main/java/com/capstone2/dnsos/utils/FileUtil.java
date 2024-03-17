package com.capstone2.dnsos.utils;

import com.capstone2.dnsos.common.KilometerMin;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.HistoryMedia;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.util.*;

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


    public static MediaType getMediaType(Resource resource) throws Exception {
        // Lấy đuôi mở rộng của tập tin để xác định kiểu nội dung
        String contentType = "application/octet-stream"; // Mặc định là kiểu dữ liệu nhị phân
        contentType = resource.getFile().toURI().toURL().openConnection().getContentType();
        return MediaType.parseMediaType(contentType);
    }

    public static HistoryMedia saveImgAndAudio(@NotNull List<MultipartFile> files, HistoryMedia historyMedia) throws Exception {
        if (files.isEmpty() || historyMedia == null) {
            throw new NotFoundException("List file empty and object history is null");
        }

        final String[] fileType = {".mp3", ".png", ".jpg","jpeg"};
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
            switch (type) {
                case ".mp3":
                    historyMedia.setVoice(uniqueFile);
                    break;
                case ".png":
                case ".jpg":
                case ".jpeg":
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
