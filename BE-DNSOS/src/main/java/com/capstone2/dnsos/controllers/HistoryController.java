package com.capstone2.dnsos.controllers;


import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.ReportDTO;
import com.capstone2.dnsos.dto.history.CancelDTO;
import com.capstone2.dnsos.dto.history.HistoryDTO;
import com.capstone2.dnsos.dto.history.NoteDTO;
import com.capstone2.dnsos.dto.history.StatusDTO;

import com.capstone2.dnsos.enums.Status;
import com.capstone2.dnsos.exceptions.exception.ForbiddenException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.exceptions.exception.UnauthorizedException;
import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.histories.*;
import com.capstone2.dnsos.services.reports.IReportService;

import com.capstone2.dnsos.repositories.main.IHistoryRepository;
import com.capstone2.dnsos.utils.FileUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Paths;

import java.security.InvalidParameterException;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("${api.prefix}/histories")
public class HistoryController {


    private final IHistoryCreateDeleteService historyCreateService;
    private final IHistoryReadService historyReadService;
    private final IHistoryUpdateService updateHistoryService;
    private final IHistoryMediaService historyMediaService;
    private final IReportService reportService;
    private final IHistoryChangeLogService logService;
    private final IHistoryUpdateService historyUpdateService;
    private final IHistoryRepository historyRepository;

    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    // kiểm t ra lại nếu chưa dừng lại không cho tạo
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createHistory(@Valid @RequestBody HistoryDTO request, BindingResult result) {
        try {
            logger.info("Bát đầu Controller createHistory ...............");
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                logger.error("Lỗi dữ liệu đầu vào: ", listError.toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ResponsesEntity.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(listError.toString())
                                .build());
            }
            CreateHistoryByUserResponses createHistory = historyCreateService.createHistory(request);
            logger.info("Kết thúc Controller createHistory ...............");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Tạo thành công tin hiệu cầu cứu", HttpStatus.CREATED.value(), createHistory));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }


    private void checkValidInDto(BindingResult result) {
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

//    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION','ROLE_USER')")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCurrentHistoryForApp(@Valid @PathVariable("id") Long id, BindingResult result) throws Exception {
//        this.checkValidInDto(result);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponsesEntity("Update status cancel successfully", HttpStatus.OK.value(), ""));
//    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/note")
    public ResponseEntity<?> updateHistoryNote(@Valid @RequestBody NoteDTO request, BindingResult result) {
        try {
            logger.info("Bát đầu Controller updateHistoryNote:.....................");
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                logger.error("Lỗi dữ liệu đầu vào:.....................", listError);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
            }
            String note = updateHistoryService.updateHistoryNote(request);
            logger.info("Kết thúc Controller updateHistoryNote:.....................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Cập nhật ghi chú thành công", HttpStatus.OK.value(), note));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }

    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/gps")
    public ResponseEntity<?> updateHistoryGPS(@Valid @RequestBody GpsDTO request, BindingResult result) {
        try {
            logger.info("Bắt đầu controller updateHistoryGPS:..............................");
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                logger.info("Lỗi dữ liệu đầu vào........................................");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
            }
            HistoryByGPSResponse historyByGPSResponse = updateHistoryService.updateHistoryGPS(request);
            logger.info("Kết thúc controller updateHistoryGPS:..............................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Cập nhật GPS cho tín hiệu cầu cứu thành công ", HttpStatus.OK.value(), historyByGPSResponse));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }

    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/user/status")
    public ResponseEntity<?> updateHistoryCancelUser(@Valid @RequestBody CancelDTO request, BindingResult result) throws Exception {
        logger.info("Bắt đầu controller updateHistoryCancelUser:..............................");
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            logger.info("Lỗi dữ liệu đầu vào........................................");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), HttpStatus.BAD_REQUEST.value(), ""));
        }
        updateHistoryService.updateHistoryStatusCancelUser(request);
        logger.info("Kêt Thúc controller updateHistoryCancelUser:..............................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Update status cancel successfully", HttpStatus.OK.value(), ""));
    }


    // Bug chua co role app
    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION','ROLE_RESCUE_WORKER')")
    @PatchMapping("/rescue_station/cancel")
    public ResponseEntity<?> updateHistoryStatusCancel(@Valid @RequestBody CancelDTO request, BindingResult result) {
        try {
            logger.info("Bát đầu controller updateHistoryStatusCancel:..............................");
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                logger.info("Lỗi dữ liệu đầu vào.....................................",listError);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(), 400, ""));
            }
            HistoryResponse historyResponse = historyUpdateService.updateHistoryStatusCancel(request);
            logger.info("Kêt thúc controller updateHistoryStatusCancel:..............................");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Hủy thành công tín hiệu cứu hộ", 200, historyResponse));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }


//    @PreAuthorize("hasAnyRole('ROLE_RESCUE')")
//    @PatchMapping("/status")
//    public ResponseEntity<?> updateStatusConfirmed(@Valid @RequestBody ConfirmedDTO request, BindingResult result) {
//        try {
//            if (result.hasErrors()) {
//                List<String> listError = result.getAllErrors()
//                        .stream()
//                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                        .toList();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(listError.toString(),400,""));
//            }
//            boolean isCheck = updateHistoryService.updateHistoryStatusConfirmed(request);
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Update successfully", 200, true));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(),400,""));
//        }
//    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION','ROLE_RESCUE_WORKER')")
    @PatchMapping("rescue_station/status")
    public ResponseEntity<?> updateStatusHistory(@Valid @RequestBody StatusDTO request, BindingResult result) throws Exception {
        try {
            logger.info("Bắt đầu Controller updateStatusHistory:..............................");
            if (result.hasErrors()) {
                List<String> listError = result.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                logger.info("Lỗi dữ liệu đầu vào.....................................",listError);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponsesEntity(listError.toString(), 400, ""));
            }
            Status status = updateHistoryService.updateHistoryStatus(request);
            if (status.getValue() >= 4) {
                logger.info("Kết thúc Controller updateStatusHistory:..............................");
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponsesEntity("Cập trạng thái tín hiệu cầu cứu thành công, kết thúc nhiệm vụ cứu hộ ", HttpStatus.OK.value(), ""));
            }
            logger.info("Kết thúc Controller updateStatusHistory:..............................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Cập trạng thái tín hiệu cầu cứu thành công, '" + status + "' trạng thái tiếp theo: "
                            + (Status.values()[(status.getValue() + 1)]), HttpStatus.OK.value(), status.getValue() + 1));
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }

    }


//    // sos ,  upload
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @PostMapping(value = "/{history_id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadMediaHistory(
//            @Valid @PathVariable("history_id") Long historyId,
//            @ModelAttribute List<MultipartFile> files) throws Exception {
//        if (files.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponsesEntity("File empty", 400, ""));
//        }
//        return ResponseEntity.ok(historyMediaService.uploadHistoryMedia(historyId, files));
//    }


//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @PostMapping(value = "/{history_id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadMediaHistory(
//            @Valid @PathVariable("history_id") Long historyId,
//            @ModelAttribute MultipartFile img1,
//            @ModelAttribute MultipartFile img2,
//            @ModelAttribute MultipartFile img3,
//            @ModelAttribute MultipartFile voice) {
//        try {
//            logger.info("Bát đàu controller uploadMediaHistory:..................................");
//            HistoryMediaResponses historyMediaResponses = historyMediaService.uploadHistoryMedia(historyId, img1, img2, img3, voice);
//            logger.info("Kết thúc controller uploadMediaHistory:..................................");
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Cập nhật thành công media", 200, historyMediaResponses));
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
//        }
//    }


    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/{history_id}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMediaHistory(
            @Valid @PathVariable("history_id") Long historyId,
            @RequestParam(value = "img1", required = false) MultipartFile img1,
            @RequestParam(value = "img2", required = false) MultipartFile img2,
            @RequestParam(value = "img3", required = false) MultipartFile img3,
            @RequestParam(value = "voice", required = false) MultipartFile voice) {
        try {
            logger.info("Bắt đầu controller uploadMediaHistory:..................................");
            HistoryMediaResponses historyMediaResponses = historyMediaService.uploadHistoryMedia(historyId, img1, img2, img3, voice);
            logger.info("Kết thúc controller uploadMediaHistory:..................................");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Cập nhật thành công media", 200, historyMediaResponses));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    //    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/media/{media_name}")
    public ResponseEntity<?> viewImage(@PathVariable("media_name") String mediaName) throws Exception {
        try {
            logger.info("Bát đàu controller medias :..................................");
            java.nio.file.Path imagePath = Paths.get("uploads/" + mediaName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                logger.info("Kết thúc controller medias :..................................");
                MediaType mediaType = FileUtil.getMediaType(resource);
                return ResponseEntity.ok().contentType(mediaType).body(resource);
            } else {
//                java.nio.file.Path notFound = Paths.get("uploads/notfound.mp4");
//                UrlResource resourceF = new UrlResource(notFound.toUri());
//                MediaType mediaType = FileUtil.getMediaType(resourceF);
//                return ResponseEntity.ok()
//                        .contentType(mediaType)
//                        .body(resourceF);
                logger.info("Kết thúc controller medias :..................................");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.NOT_FOUND.value(), ""));
        }
    }


//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @GetMapping("/{history_id}/media")
//    public ResponseEntity<?> viewImages(@PathVariable("history_id") long historyId) {
//        try {
//
//            HistoryMedia media = historyMediaService.getMediaByHistory(historyId);
//            Set<String> mediaNames = new HashSet<>();
//            mediaNames.add(media.getImage1());
//            mediaNames.add(media.getImage2());
//            mediaNames.add(media.getImage3());
//            mediaNames.add(media.getVoice());
//
//
//            Set<Resource> imageResources = new HashSet<>();
//
//            for (String name : mediaNames) {
//                java.nio.file.Path imagePath = Paths.get("./uploads/" + name);
//                Resource resource = new UrlResource(imagePath.toUri());
//
//                if (resource.exists() || resource.isReadable()) {
//                    imageResources.add(resource);
//                } else {
//                    imageResources.add(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
//                }
//            }
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResources.toString());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    // Page and limit
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> getAllHistoryByIdForAdmin(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int limit)  throws UnauthorizedException, ForbiddenException  {
        try {
            logger.info("Bát đàu controller medias :..................................");
            Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
            Page<PageHistoryResponse> pageHistoryResponse = historyReadService.getAllHistoryForAdmin(pageable);
            int totalPages = pageHistoryResponse.getTotalPages();
            long totalElements = pageHistoryResponse.getTotalElements();
            PageHistoryResponses pageHistoryResponses = PageHistoryResponses.builder()
                    .listRescueWorker(pageHistoryResponse.getContent())
                    .totalPages(totalPages)
                    .totalElements(totalElements)
                    .build();
            logger.info("Kêt thúc controller medias :..................................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Lấy thành công danh sách cầu cứu", HttpStatus.OK.value(), pageHistoryResponses));
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(e.getMessage(), 400, ""));
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<?> getDetailHistory(@PathVariable("id") Long historyId)  throws Exception,UnauthorizedException, ForbiddenException  {
        logger.info("Bát đàu controller getDetailHistory :..................................");
        DetailHistoryResponse detailHistoryResponse = historyReadService.getDetailHistoryById(historyId);
        logger.info("Kêt thúc  controller getDetailHistory :..................................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Lấy thánh công chi tiết tín hiệu cầu cứu", HttpStatus.OK.value(), detailHistoryResponse));
    }


    // Page and limit
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllHistoryByUser() throws Exception,UnauthorizedException, ForbiddenException {
        logger.info("Bát đàu controller getAllHistoryByUser :..................................");
        List<HistoryByUserResponses> listUserResponses = historyReadService.getAllHistoryByUser();
        logger.info("Kêt thúc  controller getAllHistoryByUser :..................................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), listUserResponses));
    }


    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @GetMapping("/all/rescue_station")
    public ResponseEntity<?> getAllHistoryByRescueStation()  throws Exception,UnauthorizedException, ForbiddenException {
        logger.info("Bát đàu controller getAllHistoryByRescueStation :..................................");
        List<HistoryByRescueStationResponses> listHistoryByRescueStationResponses = historyReadService.getAllHistoryByRescueStation();
        logger.info("Kêt thúc  controller getAllHistoryByRescueStation :..................................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), listHistoryByRescueStationResponses));
    }

    // Đổi lai thanh socket
    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION')")
    @GetMapping("/rescue_station")
    public ResponseEntity<?> getAllHistoryNotConfirmedAndCancel()  throws Exception,UnauthorizedException, ForbiddenException  { // view map
        logger.info("Bát đàu controller getAllHistoryNotConfirmedAndCancel :..................................");
        List<HistoryByRescueStationResponses> historiesNotConfirmedAndCancel = historyReadService.getAllHistoryNotConfirmedAndCancel();
        logger.info("Kêt thúc  controller historiesNotConfirmedAndCancel :..................................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Get all History Successfully", HttpStatus.OK.value(), historiesNotConfirmedAndCancel));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION','ROLE_RESCUE_WORKER','ROLE_USER')")
    @PostMapping("/report")
    public ResponseEntity<?> createReport(@Valid @RequestBody ReportDTO request, BindingResult result)  throws Exception,UnauthorizedException, ForbiddenException  {
        logger.info("Bát đàu controller createReport :..................................");
        if (result.hasErrors()) {
            List<String> listError = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            logger.error("Lỗi tham số đầu vào: ",listError);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(listError.toString(), 400, ""));
        }
        ReportResponse report = reportService.createReport(request);
        logger.info("Kêt thúc controller createReport :..................................");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponsesEntity("Create report successfully", 200, report));
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_STATION','ROLE_RESCUE_WORKER','ROLE_USER')")
    @GetMapping("/{history_id}/report")
    public ResponseEntity<?> getAllReportByHistoryId(@Valid @PathVariable("history_id") Long historyId)  throws UnauthorizedException, ForbiddenException {
        try {
            logger.info("Bát đàu controller getAllReportByHistoryId :..................................");
            List<ReportResponse> reports = reportService.readReports(historyId);
            logger.info("Kêt thúc controller getAllReportByHistoryId :..................................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Get report successfully", 200, reports));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    // chua
    @GetMapping("/{history_id}/log")
    public ResponseEntity<?> getLogByHistoryId(@Valid @PathVariable("history_id") Long historyId)  throws UnauthorizedException, ForbiddenException {
        try {
            logger.info("Bát đàu controller getLogByHistoryId :..................................");
            List<HistoryLogResponses> logs = logService.readLogByHistoryId(historyId);
            logger.info("Kêt thúc controller getLogByHistoryId :..................................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Get log successfully", 200, logs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentHistoryInMapUser() {
        try {
            logger.info("Bát đàu controller getCurrentHistoryInMapUser :..................................");
            HistoryInMapAppResponse response = historyReadService.getCurrentHistoryInMapUser();
            logger.info("Kêt thúc controller getCurrentHistoryInMapUser :..................................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Get log successfully", 200, response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), 400, ""));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_RESCUE_WORKER')")
    @GetMapping("/{id}/worker")
    public ResponseEntity<?> getCurrentHistoryInMapWorker(@PathVariable("id") @NotNull Long historyId) {
        try {
            logger.info("Bát đàu controller getCurrentHistoryInMapWorker :..................................");
            HistoryInMapAppResponse response = historyReadService.getCurrentHistoryByIdInMapWorker(historyId);
            logger.info("Kêt thúc controller getCurrentHistoryInMapWorker :..................................");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponsesEntity("Lấy thành công chi tiết tín hiệu cầu cứu hiện tại", HttpStatus.OK.value(), response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponsesEntity(e.getMessage(), HttpStatus.BAD_REQUEST.value(), ""));
        }
    }

//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @GetMapping("/current/{id}")
//    public ResponseEntity<?> getCurrentHistoryInMapUser(@PathVariable("id") Long historyId) {
//        try {
//            HistoryInMapAppResponse response = historyReadService.getCurrentHistoryInMapUser(historyId);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponsesEntity("Get log successfully", 200, response));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponsesEntity(e.getMessage(), 400, ""));
//        }
//    }

    @GetMapping("/rescue_station/changeStation/{historyId}")
    public ResponseEntity<?> changeRescueStation(@Valid @PathVariable("historyId") Long historyId) throws Exception {
        logger.info("Bát đàu controller changeRescueStation :..................................");
        if (!historyRepository.existsById(historyId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsesEntity("History is not existing", 400, ""));
        }
        try {
            HistoryUserResponses result = historyUpdateService.changeRescueStation(historyId);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Forwarding rescue station successfully", 200, result));
            }
        } catch (InvalidParameterException exe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity(exe.getMessage(), 400, ""));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Cannot forwarding rescue station", 400, ""));
    }

}

