package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.models.main.HistoryMedia;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHistoryMediaService {
    HistoryMedia getMediaByHistory(Long historyId) throws Exception;// id
    HistoryMedia uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception;
}
