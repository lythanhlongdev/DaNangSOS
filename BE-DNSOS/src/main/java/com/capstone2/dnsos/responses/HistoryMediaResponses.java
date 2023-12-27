package com.capstone2.dnsos.responses;

import com.capstone2.dnsos.models.HistoryMedia;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryMediaResponses {

    private Long historyId;
    private String img1;
    private String img2;
    private String img3;
    private String voice;


    public static HistoryMediaResponses mapFromEntity(HistoryMedia historyMedia) {
        return HistoryMediaResponses.builder()
                .historyId(historyMedia.getHistory().getHistoryId())
                .img1(historyMedia.getImage1())
                .img2(historyMedia.getImage2())
                .img3(historyMedia.getImage3())
                .voice(historyMedia.getVoice())
                .build();
    }
}
