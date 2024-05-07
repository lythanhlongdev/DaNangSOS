package com.capstone2.dnsos.responses.main;

import com.capstone2.dnsos.models.main.*;

import lombok.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageRescueWorkerResponse {

    private Long id;
    private String createAt;
    private String phoneNumber;
    private String passport;
    private String firstName;
    private String lastName;
    private String birthday;
    private String address;
    private String roleFamily;
    private boolean isActivity;
    private String role;
    private String userId;
    private String rescueStationId;
    private String rescueStationName;

    public static PageRescueWorkerResponse mapper(Rescue rescue) {
//        Set<String> role = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
        User user = rescue.getUser();
        RescueStation rescueStation = rescue.getRescueStations()
                .stream()
                .filter(RescueStationRescueWorker::isActivity)
                .filter(rcw -> rcw.getRescue().getId().equals(rescue.getId()))
                .findFirst()
                .map(RescueStationRescueWorker::getRescueStation)
                .orElse(null); // Trả về null nếu không tìm thấy RescueStation

        String role = user.getRoles().stream()
                .filter(role1 -> role1.getId() == 3L)
                .map(Role::getRoleName)
                .findFirst().orElse("Get Role Error");

        return PageRescueWorkerResponse.builder()
                .createAt(user.getCreatedAt().toString())
                .id(rescue.getId())
                .userId(user.getId().toString())
                .phoneNumber(user.getPhoneNumber())
                .passport(user.getPassport())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday().toString())
                .address(user.getAddress())
                .roleFamily(user.getRoleFamily())
                .isActivity(user.getIsActivity())
                .role(role.toUpperCase())
                .rescueStationId(rescueStation == null ? "" : rescueStation.getId().toString())
                .rescueStationName(rescueStation == null ? "" : rescueStation.getRescueStationsName())
                .build();
    }

}
