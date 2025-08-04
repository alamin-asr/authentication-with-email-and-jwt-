package com.alamin.jwt_advance.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY,generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen",sequenceName = "role_id_seq",allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<RolePermission>permissions=new HashSet<>();

}


