package com.capstone2.dnsos.configurations;


import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;
import com.capstone2.dnsos.models.main.History;
import com.capstone2.dnsos.models.main.HistoryMedia;
import com.capstone2.dnsos.models.main.RescueStation;
import com.capstone2.dnsos.models.main.User;

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
                .id(history.getId())
                .status(history.getStatus())
                .latitude(history.getLatitude())
                .longitude(history.getLongitude())
                .note(history.getNote())
                .user(history.getUser())
                .rescueStation(history.getRescueStation())
                .build();
    }

    public RescueStation mapperRecueStation(RescueStationDTO rescueStationDTO, User existingUser){
        return RescueStation.builder()
                .user(existingUser)
                .avatar("")
                .rescueStationsName(rescueStationDTO.getRescueStationsName())
                .latitude(rescueStationDTO.getLatitude())
                .longitude(rescueStationDTO.getLongitude())
                .phoneNumber1(existingUser.getPhoneNumber())
                .phoneNumber2(rescueStationDTO.getPhoneNumber2())
                .phoneNumber3(rescueStationDTO.getPhoneNumber3())
                .address(rescueStationDTO.getRescueStationsAddress())
                .description(rescueStationDTO.getDescription())
                .isActivity(true)
                .build();
    }

    public  User mapperUser(RescueStationDTO rescueStationDTO){
        return  User.builder()
                .phoneNumber(rescueStationDTO.getPhoneNumber())
                .passport(rescueStationDTO.getPassport())
                .firstName(rescueStationDTO.getFirstName())
                .lastName(rescueStationDTO.getLastName())
                .password(rescueStationDTO.getPassword())
                .birthday(rescueStationDTO.getBirthday())
                .address(rescueStationDTO.getAddress())
                .roleFamily(rescueStationDTO.getRoleFamily())
                .isActivity(true)
                .build();
    }
    public HistoryMedia mapperHistoryMedia(HistoryMedia newHistoryMedia){
        return HistoryMedia.builder()
                .history(newHistoryMedia.getHistory())
                .image1(newHistoryMedia.getImage1())
                .image2(newHistoryMedia.getImage2())
                .image3(newHistoryMedia.getImage3())
                .voice(newHistoryMedia.getVoice())
                .build();
    }

    public  User mapperUser(RegisterDTO registerDTO){
        return  User.builder()
                .avatar("")
                .phoneNumber(registerDTO.getPhoneNumber())
                .passport(registerDTO.getPassport())
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .password(registerDTO.getPassword())
                .birthday(registerDTO.getBirthday())
                .address(registerDTO.getAddress())
                .roleFamily(registerDTO.getRoleFamily())
                .isActivity(true)
                .build();
    }


}


//@Configuration
//public class Mappers2 {
//
//    @Bean
//    public History mapperHistory(History history) {
//        return History.builder()
//                .historyId(history.getId())
//                .status(history.getStatus())
//                .latitude(history.getLatitude())
//                .longitude(history.getLongitude())
//                .note(history.getNote())
//                .user(history.getUser())
//                .rescueStation(history.getRescueStation())
//                .build();
//    }

//}
