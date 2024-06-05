package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.repositories.main.IHistoryMediaRepository;
import com.capstone2.dnsos.responses.main.HistoryMediaResponses;
import com.capstone2.dnsos.services.histories.IHistoryChangeLogService;
import com.capstone2.dnsos.services.histories.IHistoryMediaService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryMediaServiceIml implements IHistoryMediaService {

    private final IHistoryMediaRepository historyMediaRepository;
    private final IHistoryChangeLogService historyChangeLogService;


    @Override
    public HistoryMediaResponses uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception {
        HistoryMedia newHistoryMedia = this.getMediaByHistory(historyId);
        HistoryMedia oldHistoryMedia = Mappers.getMappers().mapperHistoryMedia(newHistoryMedia);

        newHistoryMedia = historyMediaRepository.save(FileUtil.saveImgAndAudio(files, newHistoryMedia));
        historyChangeLogService.updateMediaLog(oldHistoryMedia, newHistoryMedia, "UPDATE");
        return HistoryMediaResponses.mapFromEntity(newHistoryMedia);
    }

    @Override
    public HistoryMediaResponses uploadHistoryMedia(Long historyId, MultipartFile img1, MultipartFile img2, MultipartFile img3, MultipartFile voice) throws Exception {
        // Lưu file 3gp tạm thời
        HistoryMedia newHistoryMedia = this.getMediaByHistory(historyId);
        newHistoryMedia = historyMediaRepository.save(FileUtil.saveImgAndAudio(newHistoryMedia, img1, img2, img3, voice));
        return HistoryMediaResponses.mapFromEntity(newHistoryMedia);
    }

    @Override
    public HistoryMedia getMediaByHistory(Long historyId) throws Exception {
        return historyMediaRepository.findByHistory_Id(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

//    public HistoryMedia getHistoryById(Long historyId) throws Exception {
//        return historyMediaRepository.findByHistory_Id(historyId)
//                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
//    }
}
