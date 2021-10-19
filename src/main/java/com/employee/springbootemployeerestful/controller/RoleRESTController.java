package com.employee.springbootemployeerestful.controller;

import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.entity.Role;
import com.employee.springbootemployeerestful.request.RoleRequest;
import com.employee.springbootemployeerestful.service.PermissionServiceImpl;
import com.employee.springbootemployeerestful.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleRESTController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PermissionServiceImpl permissionService;
    /*
    Role Data Repository
     */

    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<Role> getRoles() {
        List<Role> roleList = (List<Role>)roleService.getAll();
        if (roleList.isEmpty()){
            return null;
        }
        return roleList;
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public RoleRequest getRole(@PathVariable("roleId") int roleId) {
        Role role = roleService.getRoleByRoleId(roleId);
        RoleRequest roleRequest = null;
        if (role != null) {
            roleRequest = new RoleRequest();
            roleRequest.roleId = role.getRoleId();
            roleRequest.role = role;
            roleRequest.permissions= (List<Permission>) permissionService.getAll();
        }

        return roleRequest;
    }



    @RequestMapping(value = "/role/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public RoleRequest addRole(@RequestBody RoleRequest roleRequest) {
        Role r = null;

        if(roleRequest != null && roleRequest.role != null)
            r = roleService.getRoleByRoleId(roleRequest.roleId);

        if (r != null) {
            System.out.println("The role "+roleRequest.role.getRoleTitle()+" is already existed.");
            return null;
        } else {
            roleService.addRoleWithPermissions(roleRequest);
            System.out.println("The new role "+roleRequest.role.getRoleTitle()+" has been created successfully on server.");
            return roleRequest;
        }
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public RoleRequest updateRole(@RequestBody RoleRequest roleRequest) {
        Role r = roleService.getOneRole(roleRequest.role.getRoleTitle());
        if(r != null) {
            roleService.updateRoleWithPermissions(roleRequest);
            System.out.println("The role "+roleRequest.role.getRoleTitle()+" has been updated.");
            return roleRequest;
        } else {
            System.out.println("The role "+roleRequest.role.getRoleTitle()+" can not be found.");
            return null;
        }
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public int deleteRole(@PathVariable("roleId") String roleId) {
        try {
            int rid = Integer.parseInt(roleId);
            Role role = roleService.getRoleByRoleId(rid);

            if (role != null) {
                roleService.deleteRoleById(rid);
                System.out.println("The role "+role.getRoleTitle()+" has been deleted.");
                return 1;
            } else {
                System.out.println("The role "+role.getRoleTitle()+" can not be found.");;
                return 0;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
