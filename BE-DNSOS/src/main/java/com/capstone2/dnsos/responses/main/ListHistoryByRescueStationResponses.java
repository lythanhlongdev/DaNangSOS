package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListHistoryByRescueStationResponses {

    private Status status;
    private Long historyId;
    private Double latitude;// vi do
    private Double longitude;// kinh do
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private HistoryMediaResponses mediaResponses;
    private UserNotPasswordResponses userNotPasswordResponses;

//    public static ListHistoryByRescueStationResponses mapper(History history, HistoryMedia historyMedia, List<User> families) {
//        ListHistoryByRescueStationResponses responses = ListHistoryByRescueStationResponses.builder()
//                .status(history.getStatus())
//                .historyId(history.getId())
//                .latitude(history.getLatitude())
//                .longitude(history.getLongitude())
//                .note(history.getNote())
//                .createdAt(history.getCreatedAt())
//                .updatedAt(history.getUpdatedAt())
//                .build();
//        responses.setUserResponses(UserResponses.mapper(history.getUser(), families));
//        responses.setMediaResponses(HistoryMediaResponses.mapFromEntity(historyMedia));
//        return responses;
//    }
//
//    public static ListHistoryByRescueStationResponses mapToResponse(History history, IHistoryMediaRepository historyMedia, IUserRepository userRepository) {
//        HistoryMedia medias = historyMedia.findByHistory(history);
//        List<User> families = userRepository.findByFamily(history.getUser().getFamily());
//
//        return ListHistoryByRescueStationResponses.builder()
//                .status(history.getStatus())
//                .historyId(history.getId())
//                .latitude(history.getLatitude())
//                .longitude(history.getLongitude())
//                .note(history.getNote())
//                .createdAt(history.getCreatedAt())
//                .updatedAt(history.getUpdatedAt())
//                .userResponses(UserResponses.mapper(history.getUser(), families))
//                .mediaResponses(HistoryMediaResponses.mapFromEntity(medias))
//                .build();
//    }

}
