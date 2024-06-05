package com.capstone2.dnsos.services.histories;

import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.responses.main.HistoryMediaResponses;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IHistoryMediaService {
    HistoryMedia getMediaByHistory(Long historyId) throws Exception;// id

    HistoryMediaResponses uploadHistoryMedia(Long historyId, List<MultipartFile> files) throws Exception;

    HistoryMediaResponses uploadHistoryMedia(Long historyId, MultipartFile img1, MultipartFile img2, MultipartFile img3, MultipartFile voice) throws Exception;
}
