package com.employee.springbootemployeerestful.repository;

import com.employee.springbootemployeerestful.entity.Permission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPermissionRepository extends CrudRepository<Permission, Integer> {
    Permission findPermissionByPermissionId(int permissionid);
    Permission findPermissionByPermissionTitle(String permissiontitle);
    Permission findPermissionByPermissionConstantName(String permissionconstantname);
    List<Permission> findPermissionByPermissionTitleIsNotNull();
    boolean existsPermissionByPermissionTitle(String permissiontitle);
    boolean existsPermissionByPermissionConstantName(String permissionconstantname);
    int deletePermissionByPermissionTitle(String permissiontitle);
    int deletePermissionByPermissionConstantName(String permissionconstantname);
}
