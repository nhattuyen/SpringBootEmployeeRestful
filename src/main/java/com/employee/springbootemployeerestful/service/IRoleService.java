package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.Role;

public interface IRoleService{
    Iterable<Role> getAll();

    Role getOneRole(String roletitle);

    Role getRoleByRoleId(int roleid);

    int addRole(Role role);

    int updateRole(Role role);

    int deleteRoleById(int roleid);

    boolean isRoleExist(String roletitle);
}
