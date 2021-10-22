package com.employee.springbootemployeerestful.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RoleId", nullable = false)
    @EqualsAndHashCode.Exclude
    private int roleId;

    @Column(name = "roleTitle", length = 60, nullable = false)
    @EqualsAndHashCode.Exclude
    private String roleTitle;

    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appUserId")
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<AppUser> appUsers = new ArrayList<AppUser>();

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "permissionId")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name="PermissionId"), inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Collection<Permission> permissions = new ArrayList<Permission>();

    public Collection<AppUser> getAppUsers() {
        return appUsers;
    }

    public  void setAppUsers(Collection<AppUser> appusers) {
        this.appUsers = appusers;
    }
}
