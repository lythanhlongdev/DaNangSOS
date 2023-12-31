package com.capstone2.dnsos.repositories.address;

import com.capstone2.dnsos.models.address.District;
import com.capstone2.dnsos.models.address.Ward;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Qualifier("AddressDatabaseConfig")
public interface IWardRepository extends JpaRepository<Ward, String> {

    List<Ward> findAllByDistrictCode(District DistrictCode) throws Exception;
}
