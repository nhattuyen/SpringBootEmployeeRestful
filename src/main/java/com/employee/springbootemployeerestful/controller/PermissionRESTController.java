package com.employee.springbootemployeerestful.controller;

import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.service.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermissionRESTController {

    @Autowired
    private PermissionServiceImpl permissionService;

    /*
     * Permission Data Repository
     */

    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<Permission> getAllPermissions() {
        List<Permission> permissionList = (List<Permission>) permissionService.getAll();
        if (permissionList.isEmpty()) {
            return null;
        }
        return permissionList;
    }

    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Permission getPermission(@PathVariable("permissionId") int permissionId) {
        return permissionService.getOnePermissionByPermissionId(permissionId);
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Permission addPermission(@RequestBody Permission permission) {
        Permission existedPermission = permissionService.getOnePermissionByPermissionId(permission.getPermissionId());
        if (existedPermission != null) {
            System.out.println("The permission you tried to create "+permission.getPermissionTitle()+" is already existed.");
            return null;
        } else {
            permissionService.addPermissionByPermission(permission);
            System.out.println("The new permission "+permission.getPermissionConstantName()+"has been created successfully on server");
            return permission;
        }
    }

    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Permission updatePermission(@RequestBody Permission permission, @PathVariable String permissionId) {
        permission.setPermissionId(Integer.parseInt(permissionId));
        Permission existedPermission = permissionService.getOnePermissionByPermissionId(Integer.parseInt(permissionId));
        if (existedPermission != null) {
            permissionService.updatePermissionByPermission(permission);
            System.out.println("The permission "+permission.getPermissionTitle()+" has been updated.");
            return permission;
        } else {
            System.out.println("The permission "+permission.getPermissionTitle()+" can not be found.");
            return null;
        }
    }

    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public int deletePermission(@PathVariable("permissionId") String permissionId) {
        try
        {
            int pid = Integer.parseInt(permissionId);
            Permission p = permissionService.getOnePermissionByPermissionId(pid);
            if (p != null) {
                permissionService.deletePermissionByPermissionConstantName(p.getPermissionConstantName());
                System.out.println("The permission "+permissionId+" has been deleted");
                return 1;
            }
            else {
                System.out.println("The permission "+permissionId+" can not be found");
            }
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }

    }
}
