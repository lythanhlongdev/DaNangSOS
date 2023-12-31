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
@Table(name = "administrative_units")
public class AdministrativeUnit {

    @Id
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "full_name_en")
    private String fullNameEn;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "short_name_en")
    private String shortNameEn;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_name_en")
    private String codeNameEn;

    // Constructor, getters, setters, and other methods
}
