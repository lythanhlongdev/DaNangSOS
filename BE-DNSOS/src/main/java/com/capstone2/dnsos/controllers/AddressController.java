package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.repositories.address.IDistrictRepository;
import com.capstone2.dnsos.responses.address.DistrictResponse;
import com.capstone2.dnsos.responses.address.ProvinceResponse;
import com.capstone2.dnsos.responses.address.WardResponse;
import com.capstone2.dnsos.services.address.IAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final IAddressService addressService;

    @GetMapping("/provinces")
    public ResponseEntity<?> getAllProvince() {
        try {
            List<ProvinceResponse> provinces = addressService.getAllProvince();
            logger.info("Retrieved all provinces successfully.");
            return ResponseEntity.ok(provinces);
        } catch (Exception e) {
            logger.error("Error retrieving provinces: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/districts/{provinceCode}")
    public ResponseEntity<?> getDistrictsByProvinceCode(@PathVariable("provinceCode") String provinceCode) {
        try {
            List<DistrictResponse> districts = addressService.getDistrictByProvinceCode(provinceCode);
            logger.info("Retrieved districts for province {} successfully.", provinceCode);
            return ResponseEntity.ok(districts);
        } catch (Exception e) {
            logger.error("Error retrieving districts for province {}: {}", provinceCode, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error retrieving districts: " + e.getMessage());
        }
    }

    @GetMapping("/wards/{districtCode}")
    public ResponseEntity<?> getWardsByDistrictCode(@PathVariable("districtCode") String districtCode) {
        try {
            List<WardResponse> wards = addressService.getWardByDistrictCode(districtCode);
            logger.info("Retrieved wards for district {} successfully.", districtCode);
            return ResponseEntity.ok(wards);
        } catch (Exception e) {
            logger.error("Error retrieving wards for district {}: {}", districtCode, e.getMessage(), e);
            return ResponseEntity.badRequest().body("Error retrieving wards: " + e.getMessage());
        }
    }
}
