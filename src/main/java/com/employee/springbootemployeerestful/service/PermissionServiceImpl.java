package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.repository.IPermissionRepository;
import com.employee.springbootemployeerestful.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService{

    @Autowired
    private IPermissionRepository permissionRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Iterable<Permission> getAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getOnePermissionByPermissionId(int permissionid) {
        return permissionRepository.findPermissionByPermissionId(permissionid);
    }

    @Override
    public Permission getOnePermissionByPermissionTitle(String permissiontitle) {
        return permissionRepository.findPermissionByPermissionTitle(permissiontitle);
    }

    @Override
    public Permission getOnePermissionByPermissionConstantName(String permissionconstantid) {
        return permissionRepository.findPermissionByPermissionConstantName(permissionconstantid);
    }

    @Override
    public int addPermissionByPermission(Permission permission) {
        if (!isPermissionExist(permission.getPermissionId())) {
            permissionRepository.save(permission);
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePermissionByPermission(Permission permission) {
        if (isPermissionExist(permission.getPermissionId())) {
            permissionRepository.save(permission);
            return 1;
        }
        return 0;
    }

    @Override
    public int deletePermissionByPermissionTitle(String permissiontitle) {
        Permission permission = permissionRepository.findPermissionByPermissionTitle(permissiontitle);
        if (permission != null) {
            permissionRepository.deleteById(permission.getPermissionId());
            return 1;
        }
        return 0;
    }

    @Override
    public int deletePermissionByPermissionConstantName(String permissionconstantname) {
        Permission permission = permissionRepository.findPermissionByPermissionConstantName(permissionconstantname);
        if (permission != null) {
            permissionRepository.deleteById(permission.getPermissionId());
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isPermissionExist(int permissionid) {
        if(permissionRepository.findPermissionByPermissionId(permissionid) != null)
            return true;
        return false;
    }
}
