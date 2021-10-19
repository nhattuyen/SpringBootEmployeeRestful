package com.employee.springbootemployeerestful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPUSER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AppUserId", nullable = false)
    @EqualsAndHashCode.Exclude
    private int appUserId;

    @Column(name = "Username", length = 30, nullable = false)
    @EqualsAndHashCode.Exclude
    private String username;

    @Column(name = "Password", length = 30, nullable = false)
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "Email", length = 128, nullable = false)
    @EqualsAndHashCode.Exclude
    private String email;

    @Column(name = "IsSuperAdmin", nullable = false)
    @EqualsAndHashCode.Exclude
    private boolean isSuperAdmin;

    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RoleId")
    @EqualsAndHashCode.Exclude
    //@ToString.Exclude
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
