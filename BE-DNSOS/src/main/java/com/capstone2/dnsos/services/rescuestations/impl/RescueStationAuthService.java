package com.capstone2.dnsos.services.rescuestations.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.RescueStationDTO;
import com.capstone2.dnsos.dto.UpdateRescueDTO;
import com.capstone2.dnsos.enums.StatusRescueStation;
import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;
import com.capstone2.dnsos.responses.main.AvatarResponse;
import com.capstone2.dnsos.responses.main.RescueForAdminResponses;
import com.capstone2.dnsos.responses.main.RescueStationResponses;
import com.capstone2.dnsos.services.rescuestations.IRescueStationAuthService;
import com.capstone2.dnsos.utils.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RescueStationAuthService implements IRescueStationAuthService {

    private final IRescueStationRepository rescueStationRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IFamilyRepository familyRepository;
    private final PasswordEncoder passwordEncoder;
    private final IHistoryRepository historyRepository;
    private final String ADMIN = "ADMIN";

    @Transactional
    @Override
    public RescueStationResponses register(RescueStationDTO rescueStationDTO) throws Exception {
        String phoneNumber = rescueStationDTO.getPhoneNumber();

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicatedException("Phone number already exists");
        }

        User newUser = Mappers.getMappers().mapperUser(rescueStationDTO);
        String phoneFamily = rescueStationDTO.getPhoneFamily();

        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(phoneFamily)
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + phoneFamily));

        newUser.setFamily(family);
        newUser.setRoles(Set.of(
                roleRepository.findById(2L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 2")),
                roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")))
        );

        newUser.setPassword(passwordEncoder.encode(rescueStationDTO.getPassword()));
        newUser = userRepository.save(newUser);

        RescueStation newRescue = rescueStationRepository.save(Mappers.getMappers().mapperRecueStation(rescueStationDTO, newUser));
        return RescueStationResponses.mapFromEntity(newRescue);
    }

    @Override
    public RescueStationResponses updateAvatar(MultipartFile avatar) throws Exception {
        RescueStation rescueStation = getRescueStation(currenUser().getPhoneNumber());
        rescueStation.setAvatar(FileUtil.saveAvatar(avatar, rescueStation.getId(), 2));
        rescueStation = rescueStationRepository.save(rescueStation);
        return RescueStationResponses.mapFromEntity(rescueStation);
    }

    @Override
    public AvatarResponse getAvatar() throws Exception {
        // Check if rescueStation or rescueStation.getAvatar() is null
        RescueStation rescueStation = getRescueStation(currenUser().getPhoneNumber());
        if (rescueStation == null || rescueStation.getAvatar() == null) {
            return AvatarResponse.builder()
                    .avatarName("")
                    .userId(0L) // Assuming userId is a String; adjust if it's not
                    .build();
        }
    
        // Now check if the avatar name is blank
        String avatarName = rescueStation.getAvatar();
        if (avatarName.isBlank()) {
            avatarName = ""; // Or provide a default value here if needed
        }
    
        return AvatarResponse.builder()
                .avatarName(avatarName)
                .userId(rescueStation.getId())
                .build();
    }

    @Override
    public RescueStationResponses UpdateInfoRescue(UpdateRescueDTO updateRescueDTO) throws Exception {
        if (!passwordEncoder.matches(updateRescueDTO.getPassword(), this.currenUser().getPassword())) {
            throw new InvalidParamException("Invalid password");
        }
        String phoneNumber = this.currenUser().getPhoneNumber();
        RescueStation existingRescue = this.getRescueStation(phoneNumber);
        existingRescue.setRescueStationsName(updateRescueDTO.getRescueStationsName());
        existingRescue.setLatitude(updateRescueDTO.getLatitude());
        existingRescue.setLongitude(updateRescueDTO.getLongitude());
        existingRescue.setPhoneNumber2(updateRescueDTO.getPhoneNumber2());
        existingRescue.setPhoneNumber3(updateRescueDTO.getPhoneNumber3());
        existingRescue.setAddress(updateRescueDTO.getRescueStationsAddress());
        existingRescue.setDescription(updateRescueDTO.getDescription());
        User existingUser = existingRescue.getUser();
        existingUser.setPassport(updateRescueDTO.getPassport());
        existingUser.setFirstName(updateRescueDTO.getFirstName());
        existingUser.setLastName(updateRescueDTO.getLastName());
        existingUser.setBirthday(updateRescueDTO.getBirthday());
        existingUser.setAddress(updateRescueDTO.getAddress());

        Family family = updateRescueDTO.getPhoneFamily().isEmpty() ? familyRepository.save(new Family()) :
                userRepository.findByPhoneNumber(updateRescueDTO.getPhoneFamily())
                        .map(User::getFamily)
                        .orElseThrow(() -> new NotFoundException("Cannot find family with phone number: " + updateRescueDTO.getPhoneFamily()));

        existingUser.setFamily(family);

        existingUser.setRoleFamily(updateRescueDTO.getRoleFamily());
        existingRescue.setUser(existingUser);
        rescueStationRepository.save(existingRescue);
//        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()
//                -> new NotFoundException("Cannot find Rescue Station with phone: " + phoneNumber));

        return RescueStationResponses.mapFromEntity(existingRescue);
    }

    @Override
    public List<RescueForAdminResponses> getAllRecue(Pageable pageable) throws Exception {
        return rescueStationRepository
                .findAll(pageable)
                .stream()
                .map(RescueForAdminResponses::mapFromEntity)
                .toList();
    }

    @Override
    public RescueStationResponses getInfoRescue() throws Exception {
        String phoneNumber = this.currenUser().getPhoneNumber();
        RescueStation exitingRescue = this.getRescueStation(phoneNumber);
        return RescueStationResponses.mapFromEntity(exitingRescue);
    }

    @Override
    public RescueStationResponses updateStatus(Long rescueStationId, int statusId) throws Exception {
        if (statusId < 1 || statusId > 3) {
            throw new InvalidParamException("Input statusId between 1 -> 3 ");
        }
        User currentUser = this.currenUser();
        if (currentUser.getRoles().stream().noneMatch(p -> p.getRoleName().equalsIgnoreCase(ADMIN))) {
            if (currentUser.getRescueStation() != null && !currentUser.getRescueStation().getId().equals(rescueStationId)) {
                throw new InvalidParamException("You cannot update the status of another station. Please check your ID parameter");
            }
        }
        RescueStation existingRecueStation = rescueStationRepository.findById(rescueStationId)
                .orElseThrow(
                        () -> new NotFoundException("Cannot find rescue station with Id: " + rescueStationId));
        if (existingRecueStation.getStatus().getValue() == statusId) {
            throw new InvalidParamException("Cannot update because the current station status is: " + existingRecueStation.getStatus());
        }
        if (statusId == StatusRescueStation.ACTIVITY.getValue()) {
            existingRecueStation.setStatus(StatusRescueStation.ACTIVITY);
        } else if (statusId == StatusRescueStation.PAUSE.getValue()) {
            existingRecueStation.setStatus(StatusRescueStation.PAUSE);
        } else {
            existingRecueStation.setStatus(StatusRescueStation.OVERLOAD);
        }
        existingRecueStation = rescueStationRepository.save(existingRecueStation);
        return RescueStationResponses.mapFromEntity(existingRecueStation);
    }

    private RescueStation getRescueStation(String phoneNumber) throws Exception {
        return rescueStationRepository.findByPhoneNumber1(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Cannot find Rescue Station with phone: " + phoneNumber));
    }


    private User currenUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
