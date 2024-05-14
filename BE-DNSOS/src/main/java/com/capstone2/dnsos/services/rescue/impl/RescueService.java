package com.capstone2.dnsos.services.rescue.impl;

import com.capstone2.dnsos.configurations.Mappers;
import com.capstone2.dnsos.dto.GpsDTO;
import com.capstone2.dnsos.dto.UpdateWorkerDTO;
import com.capstone2.dnsos.dto.user.RegisterDTO;

import com.capstone2.dnsos.exceptions.exception.DuplicatedException;
import com.capstone2.dnsos.exceptions.exception.InvalidParamException;
import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.main.*;
import com.capstone2.dnsos.repositories.main.*;

import com.capstone2.dnsos.responses.main.*;
import com.capstone2.dnsos.services.rescue.IRescueService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RescueService implements IRescueService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IFamilyRepository familyRepository;
    private final IRescueRepository rescueRepository;
    private final PasswordEncoder passwordEncoder;
    private final IHistoryRepository historyRepository;
    private final IHistoryRescueRepository historyRescueRepository;
    private final IHistoryMediaRepository historyMediaRepository;
    private final IRescueStationRescueWorkerRepository rescueStationRescueWorkerRepository;

    private boolean isCurrentWorkerInCurrentHistory = true;

    private User userInAuth() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @Transactional
    @Override
    public RescueResponse register(RegisterDTO registerDTO) throws Exception {
        RescueStation isRescueStation = this.userInAuth().getRescueStation();
        boolean checkAccountWorker = userRepository.existsByPhoneNumber(registerDTO.getPhoneNumber());
        if (checkAccountWorker) {
            User user = userRepository.findByPhoneNumber(registerDTO.getPhoneNumber()).orElseThrow(() ->
                    new DuplicateRequestException("Lỗi do có người vô ĐB thay thổi dữ liệu dấn đến logic thứ 2 bị hỏng, Không tìm thấy người dùng có số điện thoại: " + registerDTO.getPhoneNumber()));
            boolean isRoleWorker = user.getRoles().stream().anyMatch(role -> role.getId().equals(3L));
            if (isRoleWorker) {

                Rescue rescueWorker = user.getRescues();
                RescueStationRescueWorker workerIsActivity = rescueStationRescueWorkerRepository.findByRescueAndIsActivity(rescueWorker, true);
                if (workerIsActivity != null) { // Chỗ này lỗi nếu như trong bàng rescue station worker  không được thêm id của recue
                    if (!workerIsActivity.getRescueStation().getId().equals(isRescueStation.getId())) {
                        throw new InvalidParamException("Số điện thoại đã được đăng ký làm Nhân viên ở trạm: " + workerIsActivity.getRescueStation().getRescueStationsName());
                    } else {
                        throw new DuplicatedException("Nhân viên đã tồn tại, vui lòng kiểm tra lại số điện thoại trong dánh sach nhân viên của trạm");
                    }
                } else {// lỡ như nó tôn taị mà trang thái false nên doạn trên không thực hiện dẫ đến sẽ lỗi dòng if 120     if (listWorker.stream().noneMatch(RescueStationRescueWorker::isActivity))
                    throw new Exception("Lỗi ĐB: Tài khoản nhân viên đã tồn tại, nhưng vì lý do nào đó không được thêm vào bang trung gian của trạm và nhân viên");
                }
            } else {
                if (user.getRescues() != null) {
                    Rescue oldRescue = user.getRescues();
                    List<RescueStationRescueWorker> listWorker = rescueStationRescueWorkerRepository.findAllByRescue(oldRescue);
                    if (listWorker != null) {
                        if (listWorker.stream().noneMatch(RescueStationRescueWorker::isActivity)) {
                            // lấy danh sách nhân viên trạm hiện tại
                            List<RescueStationRescueWorker> isWorkerInThisStation = listWorker.stream()
                                    .filter(rs -> rs.getRescueStation().getId().equals(isRescueStation.getId())).toList();
                            // isWorkerInThisStation mà bị null code optionalWorker sẽ bị lỗi
                            // kiểm tra xem có phải nhân viên từng làm ở trạn mình không
                            Optional<RescueStationRescueWorker> optionalWorker = isWorkerInThisStation.stream()
                                    .filter(rcw -> rcw.getRescue().getId().equals(oldRescue.getId()))
                                    .findFirst();
                            // nếu phải cấp quyền lại và mở trạng thái isActivity = true
                            if (optionalWorker.isPresent()) {
                                RescueStationRescueWorker oldRescueWorker = optionalWorker.get();
                                Set<Role> roles = Set.of(
                                        roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")),
                                        roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 4")));
                                user.setRoles(roles);
                                user.setFirstName(registerDTO.getFirstName());
                                user.setLastName(registerDTO.getLastName());
                                user.setRoleFamily(registerDTO.getRoleFamily());
                                String phoneFamily = registerDTO.getPhoneFamily();
                                Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family())
                                        : userRepository.findByPhoneNumber(phoneFamily)
                                        .map(User::getFamily)
                                        .orElseThrow(
                                                () -> new NotFoundException(
                                                        "Không thể thìm thấy giá đình của bạn với số điện thoại: " + phoneFamily));
                                user.setFamily(family);
                                user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                                user.setAddress(registerDTO.getAddress());
                                oldRescueWorker.setActivity(true);
                                oldRescueWorker = rescueStationRescueWorkerRepository.save(oldRescueWorker);
                                return RescueResponse.rescueMapperResponse(oldRescue, oldRescueWorker);
                            } else {// nếu nó không phải nhân viên của mình và nó đã nghỉ làm chỗ mới
                                // save lỗi
                                Set<Role> roles = Set.of(
                                        roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")),
                                        roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 4")));
                                user.setRoles(roles);
                                user.setFirstName(registerDTO.getFirstName());
                                user.setLastName(registerDTO.getLastName());
                                user.setRoleFamily(registerDTO.getRoleFamily());
                                String phoneFamily = registerDTO.getPhoneFamily();
                                Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family())
                                        : userRepository.findByPhoneNumber(phoneFamily)
                                        .map(User::getFamily)
                                        .orElseThrow(
                                                () -> new NotFoundException(
                                                        "Không thể thìm thấy giá đình của bạn với số điện thoại: " + phoneFamily));
                                user.setFamily(family);
                                user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
                                user.setAddress(registerDTO.getAddress());
                                RescueStationRescueWorker rescueWorker = RescueStationRescueWorker.builder()
                                        .rescueStation(isRescueStation)
                                        .rescue(oldRescue)
                                        .build();
                                rescueWorker = rescueStationRescueWorkerRepository.save(rescueWorker);
                                return RescueResponse.rescueMapperResponse(oldRescue, rescueWorker);
                            }
                        }
                    } else {
                        throw new Exception("Đữ liệu database không khớp, có thể do ai đó xóa hoặc là input dữ liệu thiếu");
                    }
                } else {
                    throw new DuplicatedException("Số điện thoại đã tồn tại và là người dùng bình thường tạm thời chưa sử lý nâng quyền cho tài khoản này");
                }
            }
        }
        // Mapper
        User newUser = Mappers.getMappers().mapperUser(registerDTO);
        Set<Role> roles = Set.of(
                roleRepository.findById(3L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 3")),
                roleRepository.findById(4L).orElseThrow(() -> new NotFoundException("Cannot find role with id: 4")));
        // Set Role
        String phoneFamily = registerDTO.getPhoneFamily();
        newUser.setRoles(roles);
        // Set Family
        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family())
                : userRepository.findByPhoneNumber(phoneFamily)
                .map(User::getFamily)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Không thể thìm thấy giá đình của bạn với số điện thoại: " + phoneFamily));
        newUser.setFamily(family);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // save user
        newUser = userRepository.save(newUser);
        // save new rescue
        Rescue newRescue = Rescue.builder().user(newUser).build();
        // Create Rescue and create, mapper response
        newRescue = rescueRepository.save(newRescue);
        // Check rescue is activity
        // Add worker to station
        RescueStationRescueWorker worker = RescueStationRescueWorker.builder()
                .rescue(newRescue)
                .rescueStation(this.userInAuth().getRescueStation())
                .build();
        rescueStationRescueWorkerRepository.save(worker);
        return RescueResponse.rescueMapperResponse(newRescue, worker);
    }

    // Lỗi chỗ này
    // private History checkQRScanningCondition(Rescue rescue) {
    // List<Status> notInStatus = Arrays.asList(Status.COMPLETED, Status.CANCELLED,
    // Status.CANCELLED_USER);
    // return historyRepository.findByRescuesAndStatusNotIn(Set.of(rescue),
    // notInStatus);
    // }

    @Transactional
    @Override
    public RescueByHistoryResponse scanQrCode(GpsDTO gpsDTO) throws Exception {
        History existingHistory = this.getHistoryById(gpsDTO.getHistoryId());
        User currentUser = this.userInAuth();
        Rescue rescue = this.getRescue(existingHistory, currentUser);

        rescue.setLongitude(gpsDTO.getLongitude());
        rescue.setLatitude(gpsDTO.getLatitude());
        rescue = rescueRepository.save(rescue);
        // set trang thai co nguoi quyet roi
        existingHistory.setDeleted(true);
        // nếu quet lai nhiệm vụ hiện tại không sinh ra thêm bản ghi trong history_resuce
        if (isCurrentWorkerInCurrentHistory) {
            HistoryRescue historyRescue = HistoryRescue.builder()
                    .history(existingHistory)
                    .rescue(rescue)
                    .build();

            historyRescue = historyRescueRepository.save(historyRescue);
        }

        HistoryMedia media = historyMediaRepository.findByHistory_Id(existingHistory.getId()).orElse(null);
        return RescueByHistoryResponse.rescueMapperHistory(existingHistory, rescue, media);
    }
    // tối ứu lại

    private Rescue getRescue(History existingHistory, User currentUser) throws Exception {
        // lấy thông tin trạm
        RescueStation rescueStation = existingHistory.getRescueStation();
        // Lấy Nhân viên ra xem
        Rescue rescue = currentUser.getRescues();
        // Kiểm tra xem nhân viên này còn thuộc trạm này không và có còn hoạt động không
        // Nếu không bảo lỗi có cho qua
        List<RescueStationRescueWorker> listWorkers = rescueStationRescueWorkerRepository
                .findAllByRescueStationAndIsActivity(rescueStation, true);

        Rescue currentRescueWorker = listWorkers.stream()
                .map(RescueStationRescueWorker::getRescue)
                .filter(rcw -> rcw.getId().equals(rescue.getId()))
                .findFirst()
                .orElse(null);

        // tại sao đoạn này lại qua được nhỉ
        if (currentRescueWorker == null) {
            throw new InvalidParamException("Không thể nhận nhiệm vụ, vì bạn không phải nhân viên của trạm: "
                    + rescueStation.getRescueStationsName());
        } else if (existingHistory.getStatus().getValue() == 0 || existingHistory.getStatus().getValue() >= 4) {
            throw new InvalidParamException(
                    "Bạn không thể nhận nhiệm vụ này bởi vì nó đang trong trạng thái: " + existingHistory.getStatus());
        } else if (existingHistory.isDeleted()) {
            // Kiem tra xem phai nhan vien cu dang quet khong
            boolean isCurrentWorker = existingHistory.getHistoryRescue().stream()
                    .filter(hw -> !hw.isCancel())
                    .map(HistoryRescue::getRescue)
                    .anyMatch(rcw -> rcw.getId().equals(currentRescueWorker.getId()));
            if (!isCurrentWorker) {
                throw new InvalidParamException("Bạn không thể nhận nhiệm vụ, vì đã có nhân viên đã nhận");
            } else {
                // ngược lại nếu nhân viện hiện tại bỏ qua đoạn code đăng sau không cập nhật lại trạng thái 
                isCurrentWorkerInCurrentHistory = false;
                return rescue;
            }
        }
        // kiếm tra xem nếu như nhiệm vụ cũ chưa hoàn thành mã đã quyet nhiệm vụ mới thì
        // sẽ hủy nhiệm vụ cũ đi
        List<HistoryRescue> checkHistoryRescue = historyRescueRepository.findAllByRescueAndCancel(rescue, false);
        if (!checkHistoryRescue.isEmpty()) {
            boolean checkUpdate = false;
            for (HistoryRescue item : checkHistoryRescue) {
                // Kiểm tra xem history của item có tồn tại không
                History history = item.getHistory();

                if (history != null) {
                    /*
                     * Kiểm tra xem status của history có đang trong giai đoạn cứu hay không và có
                     * phải khác nhiệm vụ cứu hiện tại không
                     * nếu có
                     * chuyển trạng thái hủy true => coi như hủy nhiệm vụ cũ và set lại isdelete của
                     * history về false đẻ nhân viên khác quét
                     *
                     */
                    int statusValue = history.getStatus().getValue();
                    if (statusValue > 0 && statusValue < 4) {
                        if (!existingHistory.getId().equals(history.getId())) {
                            // Cập nhật cancel thành true ở trong history rescue
                            item.setCancel(true);
                            checkUpdate = true;
                            // cập nhất luôn lịch sử chưa hoàn thành đó về trạng thái isdelete = false
                            if (item.getHistory().isDeleted()) {
                                item.getHistory().setDeleted(false);
                            }
                        }
                    }
                }
            }
            if (checkUpdate) {
                historyRescueRepository.saveAllAndFlush(checkHistoryRescue);
                isCurrentWorkerInCurrentHistory = true;
            }
        }
        return rescue;
    }

    // private Rescue getRescue(History existingHistory, User currenUser) throws
    // InvalidParamException {
    // RescueStation rescueStation = existingHistory.getRescueStation();
    // Rescue rescue = currenUser.getRescues();
    // // khác trạm không cho quét
    // // nếu trạng thái lớn hơn hoặc bằng 4 không cho quét
    // if (!rescue.getRescueStation().getId().equals(rescueStation.getId())) {
    // throw new InvalidParamException("Do not accept, because you are not a member
    // of the lifeguard station: " + rescueStation.getRescueStationsName());
    // } else if (existingHistory.getStatus().getValue() == 0 ||
    // existingHistory.getStatus().getValue() >= 4) {
    // throw new InvalidParamException("You cannot accept responsibility because the
    // signal is in state: " + existingHistory.getStatus());
    // }
    // // Đoạn code sau đã bị bỏ do ý kiến của Thịnh cho phép quét nhiệm vụ mới mà
    // không cần hoàn thành nhiệm vụ cũ :))
    //// List<Status> notInStatus = Arrays.asList(Status.COMPLETED,
    // Status.CANCELLED, Status.CANCELLED_USER);
    //// List<History> histories =
    // historyRepository.findAllByRescueStationAndStatusNotIn(rescueStation,
    // notInStatus);
    //// Optional<History> oldHistory = histories.stream()
    //// .filter(history -> history.getRescues().stream().anyMatch(r ->
    // r.getId().equals(rescue.getId()))).findFirst();
    //// // Nếu chua hoành thành nhiệm vụ cũ không cho quyeets cái mới
    //// if (oldHistory.isPresent() &&
    // !oldHistory.get().getId().equals(existingHistory.getId())) {
    //// throw new InvalidParamException(String.format("Can't accept new quests,
    // please complete the old quest with id: %s, status: %s"
    //// , oldHistory.get().getId(), oldHistory.get().getStatus()));
    //// }
    // return rescue;
    // }

    @Override
    public RescueByHistoryResponse updateGPS(GpsDTO gpsDTO) throws Exception {
        User currentUser = this.userInAuth();
        History existingHistory = this.getHistoryById(gpsDTO.getHistoryId());
        Rescue existingRescue = this.getRescueByUser(currentUser);
        boolean checkRescueInHistory = existingHistory.getHistoryRescue()
                .stream()
                .filter(hr -> !hr.isCancel())
                .map(HistoryRescue::getRescue)
                .anyMatch(rcw -> rcw.getId().equals(existingRescue.getId()));
        if (!checkRescueInHistory) {
            throw new NotFoundException("Bạn không có nhiệm vụ giải cứu với id: " + existingRescue.getId());
        } else if (existingHistory.getStatus().getValue() >= 4) {
            throw new InvalidParamException(
                    "Bạn không thể cập nhât vị trí bởi vì tín hiệu cầu cứu đang ở trạng thái: " + existingHistory.getStatus());
        }
        existingRescue.setLatitude(gpsDTO.getLatitude());
        existingRescue.setLongitude(gpsDTO.getLongitude());
        rescueRepository.save(existingRescue);
        HistoryMedia media = historyMediaRepository.findByHistory(existingHistory);
        return RescueByHistoryResponse.rescueMapperHistory(existingHistory, existingRescue, media);
    }

    private History getHistoryById(Long historyId) throws Exception {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() -> new NotFoundException("Cannot find History with id: " + historyId));
    }

    private Rescue getRescueByUser(User currentUser) throws NotFoundException {
        return rescueRepository.findByUser(currentUser).orElseThrow(
                () -> new NotFoundException("Cannot find rescue with phone number: " + currentUser.getPhoneNumber()));
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream().anyMatch(rl -> rl.getId().equals(1L));
    }

    private boolean isRescueStation(User user) {
        return user.getRoles().stream().anyMatch(rl -> rl.getId().equals(2L));
    }

    // chỗ đay
    @Override
    public Page<PageRescueWorkerResponse> getAllRescueWorker(Pageable page) throws Exception {
        User currentUser = this.userInAuth();
        if (!isRescueStation(currentUser)) {
            throw new AuthException("Bạn không phải trạm cứu hộ không có quyền truy cập API này");
        }
        RescueStation rescueStation = currentUser.getRescueStation();
        return rescueStationRescueWorkerRepository.findAllRescueStationWorkersByRescueStation(rescueStation, page).map(PageRescueWorkerResponse::mapper);
    }

    @Override
    public DetailRescueWorkerResponse getDetailRescueWorkerById(Long workerId) throws Exception {
        User currentUser = this.userInAuth();
        if (!isRescueStation(currentUser)) {
            throw new AuthException("Bạn không phải trạm cứu hộ không có quyền truy cập API này");
        }
        RescueStation rescueStation = currentUser.getRescueStation();
        RescueStationRescueWorker currentWorker = rescueStationRescueWorkerRepository
                .findByRescueStationAndRescue_Id(rescueStation, workerId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên cứu hộ có id: " + workerId));
        if (!rescueStation.getId().equals(currentWorker.getRescueStation().getId())) {
            throw new InvalidParamException("Trạm cứu hộ của bạn không có nhân viên với id: " + workerId);
        }

        return DetailRescueWorkerResponse.mapper(currentWorker);
    }

    @Override
    public Page<PageRescueWorkerResponse> getAllRescueWorkerForAdmin(Pageable page) throws Exception {
        User currentUser = this.userInAuth();
        if (!isAdmin(currentUser)) {
            throw new AuthException("Bạn không phải admin không có quyền truy cập API này");
        }
        return rescueStationRescueWorkerRepository.findAll(page).map(PageRescueWorkerResponse::mapperAdmin);
    }

    @Override
    public DetailRescueWorkerResponse getDetailRescueWorkerByIdForAdmin(Long workerId) throws Exception {
        User currentUser = this.userInAuth();
        if (!isAdmin(currentUser)) {
            throw new AuthException("Bạn không phải admin không có quyền truy cập API này");
        }
        RescueStationRescueWorker currentWorker = rescueStationRescueWorkerRepository
                .findById(workerId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên cứu hộ có id: " + workerId));
        return DetailRescueWorkerResponse.mapper(currentWorker);
    }


    @Override
    public RescueResponse changeInfoRescueWorkerForStation(UpdateWorkerDTO updateWorkerDTO) throws Exception {
        if (!passwordEncoder.matches(updateWorkerDTO.getPassword(),this.userInAuth().getPassword())){
            throw  new InvalidParamException("Mật khẩu xác nhận không đúng, kiểm tra lại ");
        }
        RescueStation rescueStation = this.userInAuth().getRescueStation();
        Rescue rescue = this.getRescueWorker(updateWorkerDTO.getId());
        RescueStationRescueWorker rescueStationRescueWorker = rescueStationRescueWorkerRepository
                .findByRescueStationAndRescue_IdAndIsActivity(rescueStation, rescue.getId(), true)
                .orElseThrow(() -> new NotFoundException("Trạm của bạn không có nhân viên này hoặc nhân viên đã nghỉ làm nên bạn không có quyền sửa thông tin"));
        User user = rescue.getUser();
        user.setPassport(updateWorkerDTO.getPassport());
        user.setFirstName(updateWorkerDTO.getFirstName());
        user.setLastName(updateWorkerDTO.getLastName());
        user.setAddress(updateWorkerDTO.getAddress());
        user.setBirthday(updateWorkerDTO.getBirthday());
        user.setRoleFamily(updateWorkerDTO.getRoleFamily());
        String phoneFamily = updateWorkerDTO.getPhoneFamily();
        Family family = phoneFamily.isEmpty() ? familyRepository.save(new Family())
                : userRepository.findByPhoneNumber(phoneFamily)
                .map(User::getFamily)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Không thể thìm thấy giá đình của bạn với số điện thoại: " + phoneFamily));
        user.setFamily(family);
        userRepository.save(user);
        rescueStationRescueWorker = rescueStationRescueWorkerRepository.save(rescueStationRescueWorker);
        return RescueResponse.rescueMapperResponse(rescue, rescueStationRescueWorker);
    }

    @Override
    @Transactional
    public void deleteRescueWorker(@Valid Long id) throws Exception {
        User currentUser = this.userInAuth();
        RescueStation rescueStation = currentUser.getRescueStation();
        RescueStationRescueWorker rescueWorker = rescueStationRescueWorkerRepository
                .findByRescueStationAndRescue_IdAndIsActivity(rescueStation, id, true)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên cứu hộ có id: " + id));

        rescueWorker.setActivity(false);

        User user = rescueWorker.getRescue().getUser();
        Set<Role> roles = user.getRoles();
        roles = roles.stream().filter(r -> r.getId() != 3)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        user = userRepository.save(user);
        user.getRoles().forEach(n -> System.out.println(n.getRoleName()));
        rescueStationRescueWorkerRepository.save(rescueWorker);
    }

    private Rescue getRescueWorker(Long id) throws Exception {
        return rescueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại nhân viên cứu hộ với mã số: " + id));
    }
}