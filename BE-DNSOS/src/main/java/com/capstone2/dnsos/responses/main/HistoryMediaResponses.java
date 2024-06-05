package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.HistoryMedia;
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
                .historyId(historyMedia.getHistory().getId())
                .img1(historyMedia.getImage1().isEmpty() ? "":historyMedia.getImage1())
                .img2(historyMedia.getImage2().isEmpty() ? "":historyMedia.getImage2())
                .img3(historyMedia.getImage3().isEmpty() ? "":historyMedia.getImage3())
                .voice(historyMedia.getVoice().isEmpty() ? "":historyMedia.getVoice())
                .build();
    }
}
