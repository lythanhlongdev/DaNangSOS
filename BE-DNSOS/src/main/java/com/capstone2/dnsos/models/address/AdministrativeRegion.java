package com.capstone2.dnsos.models.address;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrative_regions")
public class AdministrativeRegion {

    @Id
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_en")
    private String codeNameEn;

    // Constructor, getters, setters, and other methods
}
