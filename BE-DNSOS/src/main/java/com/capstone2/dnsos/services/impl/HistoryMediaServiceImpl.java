package com.capstone2.dnsos.services.impl;

import com.capstone2.dnsos.logs.IHistoryLog;
import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import com.capstone2.dnsos.repositories.IHistoryMediaRepository;
import com.capstone2.dnsos.repositories.IHistoryRepository;
import com.capstone2.dnsos.services.IHistoryChangeLogService;
import com.capstone2.dnsos.services.IHistoryMediaService;
import com.capstone2.dnsos.services.IHistoryService;
import com.capstone2.dnsos.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoryMediaServiceImpl implements IHistoryMediaService {

    private final IHistoryRepository historyRepository;
    private final IHistoryMediaRepository historyMediaRepository;
    private final IHistoryLog historyLog;
    private final IHistoryService historyService;

    @Override
    public HistoryMedia getMediaByHistory(Long historyId) throws Exception {
        return null;
    }

    @Override
    public HistoryMedia uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception {
        History existingHistory = historyService.getHistoryById(historyId);
        HistoryMedia newHistoryMedia = historyMediaRepository.findByHistory(existingHistory);
        HistoryMedia oldHistoryMedia = HistoryMedia.builder()
                .history(existingHistory)
                .image1(newHistoryMedia.getImage1())
                .image2(newHistoryMedia.getImage2())
                .image3(newHistoryMedia.getImage3())
                .voice(newHistoryMedia.getVoice())
                .build();
        newHistoryMedia = historyMediaRepository.save(FileUtil.saveImgAndAudio(files, newHistoryMedia));
        historyLog.onUpdateMedia(oldHistoryMedia,newHistoryMedia);
        return newHistoryMedia;
    }
}
