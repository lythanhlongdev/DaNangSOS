package com.capstone2.dnsos.controllers;

import com.capstone2.dnsos.repositories.address.IDistrictRepository;
import com.capstone2.dnsos.responses.address.DistrictResponse;
import com.capstone2.dnsos.responses.address.ProvinceResponse;
import com.capstone2.dnsos.responses.address.WardResponse;
import com.capstone2.dnsos.responses.main.ResponsesEntity;
import com.capstone2.dnsos.services.address.IAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final IAddressService addressService;

    @GetMapping("/provinces")
    public ResponseEntity<?> getAllProvince() throws Exception {
        List<ProvinceResponse> provinces = addressService.getAllProvince();
        logger.info("Retrieved all provinces successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get provinces Successfully", 200, provinces));
    }


    @GetMapping("/districts/{province_code}")
    public ResponseEntity<?> getDistrictsByProvinceCode(@PathVariable("province_code") String provinceCode) {
        try {
            List<DistrictResponse> districts = addressService.getDistrictByProvinceCode(provinceCode);
            if (districts == null)
            {
                logger.info("List of districts is null ");
            }
            logger.info("Retrieved districts for province {} successfully.", provinceCode);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get districts Successfully", 200, districts));
        } catch (Exception e) {
            logger.error("Error retrieving districts for province {}: {}", provinceCode, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Error retrieving districts: " + e.getMessage(), 400, ""));
        }
    }


    @GetMapping("/wards/{district_code}")
    public ResponseEntity<?> getWardsByDistrictCode(@PathVariable("district_code") String districtCode) {
        try {
            List<WardResponse> wards = addressService.getWardByDistrictCode(districtCode);
            if (wards == null)
            {
                logger.info("List of wards is null ");
            }
            logger.info("Retrieved wards for district {} successfully.", districtCode);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsesEntity("Get wards Successfully", 200, wards));
        } catch (Exception e) {
            logger.error("Error retrieving wards for district {}: {}", districtCode, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsesEntity("Error retrieving wards: " + e.getMessage(), 400, ""));
        }
    }
}
