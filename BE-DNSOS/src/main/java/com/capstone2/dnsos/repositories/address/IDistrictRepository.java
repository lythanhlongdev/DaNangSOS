package com.capstone2.dnsos.repositories.address;

import com.capstone2.dnsos.models.address.District;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("AddressDatabaseConfig")
public interface IDistrictRepository extends JpaRepository<District, String> {
    List<District> findAllByProvinceCode(String provinceCode);
}
