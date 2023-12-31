package com.capstone2.dnsos.repositories.address;

import com.capstone2.dnsos.models.address.AdministrativeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
@Qualifier("AddressDatabaseConfig")
public interface IAdministrativeUnitRepository extends JpaRepository<AdministrativeUnit, Integer> {
}
