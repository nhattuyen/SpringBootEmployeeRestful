package com.employee.springbootemployeerestful.repository;

import com.employee.springbootemployeerestful.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IRoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByRoleId(int roleid);

    Role findRoleByRoleTitle(String roletitle);

    List<Role> findRolesByRoleTitleIsNotNull();

    boolean existsRoleByRoleId(int roleid);

    boolean existsRoleByRoleTitle(String roletitle);

    int deleteRoleByRoleId(int roleid);

    int deleteRoleByRoleTitle(String roletitle);
}
