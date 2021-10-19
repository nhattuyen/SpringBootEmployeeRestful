package com.employee.springbootemployeerestful.request;

import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.entity.Role;

import java.util.List;

public class RoleRequest {
    public int roleId;

    public Role role;

    public List<Permission> permissions;
}
