package com.capstone2.dnsos.repositories.address;

import com.capstone2.dnsos.models.address.Province;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Qualifier("AddressDatabaseConfig")
public interface IProvinceRepository extends JpaRepository<Province, String> {
    Optional<Province> findAllByCode(String code);

}
