package com.capstone2.dnsos.configurations;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.RescueStation;

public class Mappers {

    private static class SingletonMapper {
        private static final Mappers INSTANCE = new Mappers();
    }

    private Mappers() {
    }

    public static Mappers getMappers() {
        return SingletonMapper.INSTANCE;
    }

    public History mapperHistory(History history) {
        return History.builder()
                .historyId(history.getHistoryId())
                .status(history.getStatus())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .user(history.getUser())
                .rescueStation(history.getRescueStation())
                .build();
    }

    public RescueStation mapperRecueStation(RescueStationDTO rescueStationDTO){
        return RescueStation.builder()
                .rescueStationsName(rescueStationDTO.getRescueStationsName())
                .captain(rescueStationDTO.getCaptain())
                .phoneNumber(rescueStationDTO.getPhoneNumber())
                .password(rescueStationDTO.getPassword())
                .address(rescueStationDTO.getAddress())
                .description(rescueStationDTO.getDescription())
                .build();
    }

}


//@Configuration
//public class Mappers2 {
//
//    @Bean
//    public History mapperHistory(History history) {
//        return History.builder()
//                .historyId(history.getHistoryId())
//                .status(history.getStatus())
//                .latitude(history.getLatitude())
//                .longitude(history.getLongitude())
//                .note(history.getNote())
//                .user(history.getUser())
//                .rescueStation(history.getRescueStation())
//                .build();
//    }

//}
