package com.capstone2.dnsos.services;

import com.capstone2.dnsos.models.History;
import com.capstone2.dnsos.models.HistoryMedia;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHistoryMediaService {
    HistoryMedia getMediaByHistory(Long historyId) throws Exception;
// get id
    HistoryMedia uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception;
}
