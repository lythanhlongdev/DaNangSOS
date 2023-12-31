package com.capstone2.dnsos.services.address.impl;

import com.capstone2.dnsos.exceptions.exception.NotFoundException;
import com.capstone2.dnsos.models.address.District;
import com.capstone2.dnsos.repositories.address.IDistrictRepository;
import com.capstone2.dnsos.repositories.address.IProvinceRepository;
import com.capstone2.dnsos.repositories.address.IWardRepository;
import com.capstone2.dnsos.responses.address.DistrictResponse;
import com.capstone2.dnsos.responses.address.ProvinceResponse;
import com.capstone2.dnsos.responses.address.WardResponse;
import com.capstone2.dnsos.services.address.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressImpl implements IAddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressImpl.class);

    private final IProvinceRepository provinceRepository;
    private final IDistrictRepository districtRepository;
    private final IWardRepository wardRepository;

    @Override
    public ProvinceResponse getProvinceByCode(String provinceCode) throws NotFoundException {
        try {
            return provinceRepository.findAllByCode(provinceCode)
                    .map(ProvinceResponse::mapperEntity)
                    .orElseThrow(() -> new NotFoundException("Cannot find province with code: " + provinceCode));
        } catch (Exception e) {
            logger.error("Error retrieving province with code {}: {}", provinceCode, e.getMessage(), e);
            throw new NotFoundException("Error retrieving province with code: " + provinceCode + e);
        }
    }

    @Override
    public List<ProvinceResponse> getAllProvince() throws NotFoundException {
        try {
            List<ProvinceResponse> provinces = provinceRepository.findAll().stream()
                    .map(ProvinceResponse::mapperEntity)
                    .toList();
            logger.info("Retrieved all provinces successfully.");
            return provinces;
        } catch (Exception e) {
            logger.error("Error retrieving all provinces: {}", e.getMessage(), e);
            throw new NotFoundException("Error retrieving all provinces" + e);
        }
    }

    @Override
    public DistrictResponse getDistrictByCode(String districtCode) throws NotFoundException {
        try {
            return districtRepository.findById(districtCode)
                    .map(DistrictResponse::mapperEntity)
                    .orElseThrow(() -> new NotFoundException("Cannot find district with code: " + districtCode));
        } catch (Exception e) {
            logger.error("Error retrieving district with code {}: {}", districtCode, e.getMessage(), e);
            throw new NotFoundException("Error retrieving district with code: " + districtCode + e);
        }
    }

    @Override
    public List<DistrictResponse> getDistrictByProvinceCode(String provinceCode) throws NotFoundException {
        try {
            List<DistrictResponse> districts = districtRepository.findAllByProvinceCode(provinceCode).stream()
                    .map(DistrictResponse::mapperEntity)
                    .toList();
            logger.info("Retrieved districts for province {} successfully.", provinceCode);
            return districts;
        } catch (Exception e) {
            logger.error("Error retrieving districts for province {}: {}", provinceCode, e.getMessage(), e);
            throw new NotFoundException("Error retrieving districts for province: " + provinceCode + e);
        }
    }

    @Override
    public WardResponse getWardByCode(String wardCode) throws NotFoundException {
        try {
            return wardRepository.findById(wardCode)
                    .map(WardResponse::mapperEntity)
                    .orElseThrow(() -> new NotFoundException("Cannot find ward with code: " + wardCode));
        } catch (Exception e) {
            logger.error("Error retrieving ward with code {}: {}", wardCode, e.getMessage(), e);
            throw new NotFoundException("Error retrieving ward with code: " + wardCode + e);
        }
    }

    @Override
    public List<WardResponse> getWardByDistrictCode(String districtCode) throws NotFoundException {
        try {
            District district = District.builder().code(districtCode).build();
            List<WardResponse> wards = wardRepository.findAllByDistrictCode(district).stream()
                    .map(WardResponse::mapperEntity)
                    .toList();
            logger.info("Retrieved wards for district {} successfully.", districtCode);
            return wards;
        } catch (Exception e) {
            logger.error("Error retrieving wards for district {}: {}", districtCode, e.getMessage(), e);
            throw new NotFoundException("Error retrieving wards for district: " + districtCode + e);
        }
    }
}