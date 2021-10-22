package com.employee.springbootemployeerestful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPUSER")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AppUserId", nullable = false)
    private int appUserId;

    @Column(name = "Username", length = 30, nullable = false)
    private String username;

    @Column(name = "Password", length = 30, nullable = false)
    private String password;

    @Column(name = "Email", length = 128, nullable = false)
    private String email;

    @Column(name = "IsSuperAdmin", nullable = false)
    private boolean isSuperAdmin;

    @JsonProperty("isSuperAdmin")
    public boolean getIsSuperAdmin() {
        return this.isSuperAdmin;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
