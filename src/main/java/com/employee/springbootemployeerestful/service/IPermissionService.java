package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.Permission;

public interface IPermissionService {
    Iterable<Permission> getAll();

    Permission getOnePermissionByPermissionId(int permissionid);
    Permission getOnePermissionByPermissionTitle(String permissiontitle);
    Permission getOnePermissionByPermissionConstantName(String permissionconstantid);

    int addPermissionByPermission(Permission permission);
    int updatePermissionByPermission(Permission permission);
    int deletePermissionByPermissionTitle(String permissiontitle);
    int deletePermissionByPermissionConstantName(String permissionconstantname);

    boolean isPermissionExist(int permissionid);
}
