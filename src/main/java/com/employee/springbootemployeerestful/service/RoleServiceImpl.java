package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.entity.Role;
import com.employee.springbootemployeerestful.repository.IAppUserRepository;
import com.employee.springbootemployeerestful.repository.IPermissionRepository;
import com.employee.springbootemployeerestful.repository.IRoleRepository;
import com.employee.springbootemployeerestful.request.AppUserRequest;
import com.employee.springbootemployeerestful.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IAppUserRepository appUserRepository;

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getOneRole(String roletitle) {
        return roleRepository.findRoleByRoleTitle(roletitle);
    }

    @Override
    public Role getRoleByRoleId(int roleid) {
        return roleRepository.findRoleByRoleId(roleid);
    }

    @Override
    public int addRole(Role role) {
        if (!isRoleExist(role.getRoleTitle())) {
            roleRepository.save(role);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateRole(Role role) {
        Role r = roleRepository.findRoleByRoleId(role.getRoleId());
        if (isRoleExist(r.getRoleTitle())) {
            roleRepository.save(role);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteRoleById(int roleid) {
        Role role = roleRepository.findRoleByRoleId(roleid);

        if (isRoleExist(role.getRoleTitle())) {
            roleRepository.deleteById(roleid);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isRoleExist(String roletitle) {
        if(roleRepository.findRoleByRoleTitle(roletitle) != null)
            return true;
        return false;
    }

    public int addRoleWithPermissions(RoleRequest roleRequest) {
        Role role = new Role();
        role.setRoleId(roleRequest.roleId);
        role.setRoleTitle(roleRequest.role.getRoleTitle());
        role.setPermissions(roleRequest.role.getPermissions().stream().map(rolePermission -> {
            Permission permission = rolePermission;
            if (permission.getPermissionId() > 0) {
                permission = permissionRepository.findPermissionByPermissionId(permission.getPermissionId());
            }
            permission.addRole(role);
            return permission;
        }).collect(Collectors.toList())
        );

        return addRole(role);
    }

    public int updateRoleWithPermissions(RoleRequest roleRequest) {
        Role role = roleRepository.findRoleByRoleId(roleRequest.roleId);
        role.setRoleTitle(roleRequest.role.getRoleTitle());
        role.setPermissions(roleRequest.role.getPermissions().stream().map(rolePermission -> {
                    Permission permission = rolePermission;
                    if (permission.getPermissionId() > 0) {
                        permission = permissionRepository.findPermissionByPermissionId(permission.getPermissionId());
                    }
                    permission.addRole(role);
                    return permission;
                }).collect(Collectors.toList())
        );

        return updateRole(role);
    }

    public int addAppUserRequest(AppUserRequest appUserRequest) {

        if (appUserRequest.appUser != null && appUserRequest.appUser.getRole() != null) {
            Role role = roleRepository.findRoleByRoleId(appUserRequest.appUser.getRole().getRoleId());

            role.setAppUsers(appUserRequest.appUser.getRole().getAppUsers().stream().map(
                    roleAppUser -> {
                        AppUser appUser = roleAppUser;
                        if (appUser.getAppUserId() > 0 ) {
                            appUser = appUserRepository.findAppUsersByAppUserId(appUser.getAppUserId());
                        }
                        role.getAppUsers().add(appUser);

                        return appUser;
                    }).collect(Collectors.toList())
            );

            return updateRole(role);

        }
        return 0;
    }

    public int updateAppUserRequest(AppUserRequest appUserRequest) {
        AppUser appUser = appUserRepository.findAppUsersByAppUserId(appUserRequest.appUser.getAppUserId());
        if (appUser != null && appUser.getRole() != null) {
            Role role = roleRepository.findRoleByRoleId(appUser.getRole().getRoleId());

            role.setAppUsers(appUserRequest.appUser.getRole().getAppUsers().stream().map(
                    roleAppUser -> {
                        AppUser appUserInRole = roleAppUser;
                        if (appUserInRole.getAppUserId() > 0 ) {
                            appUserInRole = appUserRepository.findAppUsersByAppUserId(appUser.getAppUserId());
                        }
                        role.getAppUsers().add(appUserInRole);

                        return appUserInRole;
                    }).collect(Collectors.toList())
            );

            return updateRole(role);

        }
        return 0;
    }
}
