package com.capstone2.dnsos.models.main;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long id;

    @Column(name = "role_name", length = 10)
    private String roleName;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

}
