package com.employee.springbootemployeerestful.request;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.entity.Role;

import java.util.List;

public class AppUserRequest {

    public int appUserId;

    public AppUser appUser;

    public Role role;

    public List<Role> roles;
}
