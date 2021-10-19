package com.employee.springbootemployeerestful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERMISSION")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PermissionId", nullable = false)
    private int permissionId;

    @Column(name = "PermissionTitle", nullable = false)
    @EqualsAndHashCode.Exclude
    private String permissionTitle;

    @Column(name = "PermissionConstantName", nullable = false)
    @EqualsAndHashCode.Exclude
    private String permissionConstantName;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Role> roles = new ArrayList<Role>();

    public Role addRole(Role role) {
        if (role != null && !this.roles.contains(role)) {
            this.roles.add(role);
            return role;
        }
        return null;
    }
}
