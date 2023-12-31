package com.capstone2.dnsos.services.address;

import com.capstone2.dnsos.responses.address.DistrictResponse;
import com.capstone2.dnsos.responses.address.ProvinceResponse;
import com.capstone2.dnsos.responses.address.WardResponse;

import java.util.List;

public interface IAddressService {

    ProvinceResponse getProvinceByCode(String provinceByCode) throws Exception;

    List<ProvinceResponse> getAllProvince() throws Exception;

    DistrictResponse getDistrictByCode(String districtCode) throws Exception;

    List<DistrictResponse> getDistrictByProvinceCode(String provinceCode) throws Exception;

    WardResponse getWardByCode(String wardCode) throws Exception;

    List<WardResponse> getWardByDistrictCode(String districtCode) throws Exception;
}
