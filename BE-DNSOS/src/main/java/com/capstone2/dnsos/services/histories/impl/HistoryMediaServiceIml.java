package com.capstone2.dnsos.services.histories.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.repositories.main.IHistoryMediaRepository;
import com.capstone2.dnsos.repositories.main.IHistoryRepository;
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
    private final IHistoryRepository historyRepository;


    @Override
    public HistoryMedia uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception {
        History existingHistory = getHistoryById(historyId);
        HistoryMedia newHistoryMedia = historyMediaRepository.findByHistory(existingHistory);
        HistoryMedia oldHistoryMedia = HistoryMedia.builder()
                .history(existingHistory)
                .image1(newHistoryMedia.getImage1())
                .image2(newHistoryMedia.getImage2())
                .image3(newHistoryMedia.getImage3())
                .voice(newHistoryMedia.getVoice())
                .build();
        newHistoryMedia = historyMediaRepository.save(FileUtil.saveImgAndAudio(files, newHistoryMedia));
        historyChangeLogService.updateMediaLog(oldHistoryMedia, newHistoryMedia,"UPDATE");
        return newHistoryMedia;
    }

    @Override
    public HistoryMedia getMediaByHistory(Long historyId) throws Exception {
        return null;
    }

    public History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }
}
