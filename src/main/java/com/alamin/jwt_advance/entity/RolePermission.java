package com.alamin.jwt_advance.entity;

import com.alamin.jwt_advance.enums.Permission;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

//import java.security.Permission;

@Entity
@Getter
@Setter

@Table(name = "role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_permission_id_gen")
    @SequenceGenerator(name = "role_permission_id_gen",sequenceName = "role_permission_id_seq",allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    @JsonBackReference
    private Permission permission;


}
