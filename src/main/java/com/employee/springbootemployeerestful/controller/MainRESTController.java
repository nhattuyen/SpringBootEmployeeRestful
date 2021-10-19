package com.employee.springbootemployeerestful.controller;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.entity.Employee;
import com.employee.springbootemployeerestful.entity.Permission;
import com.employee.springbootemployeerestful.entity.Role;
import com.employee.springbootemployeerestful.request.AppUserRequest;
import com.employee.springbootemployeerestful.request.RoleRequest;
import com.employee.springbootemployeerestful.service.AppUserServiceImpl;
import com.employee.springbootemployeerestful.service.EmployeeServiceImpl;
import com.employee.springbootemployeerestful.service.PermissionServiceImpl;
import com.employee.springbootemployeerestful.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class MainRESTController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private PermissionServiceImpl permissionService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private AppUserServiceImpl appUserService;

    /*
    Employee Data Repository
     */

    @RequestMapping("/")
    //@CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public String Welcome() {
        return "This is RestEmployee example";
    }

    //@CrossOrigin//(origins = "http://localhost:8080", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET})
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Employee> getEmployees() {
        List<Employee> employeeList = (List<Employee>) employeeService.getAll();
        if(employeeList.isEmpty()){
            return null;
        }
        return employeeList;
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee/{empNo}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Employee getEmployee(@PathVariable("empNo") String empNo) {
        return employeeService.getOneEmployeeByEmpNo(empNo);
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee emp) {
        Employee e = employeeService.getOneEmployeeByEmpNo(emp.getEmpNo());
        if(e != null) {
            System.out.println("The employee " + emp.getFullname() + " is already existed.");
            return null;
        } else {
            employeeService.addEmployee(emp);
            System.out.println("The new employee "+emp.getFullname()+" has been created successfully on server.");
            return emp;
        }
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee e = employeeService.getOneEmployeeByEmpNo(employee.getEmpNo());
        if(e != null) {
            employeeService.updateEmployee(employee);
            System.out.println("The employee " + employee.getFullname() + " has been updated.");
            return employee;
        }
        else {
            System.out.println("The employee "+employee.getFullname()+" can not be founded.");
            return null;
        }
    }

    //@CrossOrigin
    @RequestMapping(value = "/employee/{empNo}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteEmployee(@PathVariable("empNo") String empNo) {
        Employee e = employeeService.getOneEmployeeByEmpNo(empNo);
        if(e != null) {
            employeeService.deleteEmployee(e.getId());
            System.out.println("The employee "+empNo+" has been deleted.");
        } else {
            System.out.println("The employee "+empNo+" can not be found.");
        }

    }

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

    /*
     * AppUser Data Repository
     */

    @RequestMapping(value = "/appusers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<AppUser> getAppUsers() {
        List<AppUser> appUserList = (List<AppUser>) appUserService.getAll();
        if (appUserList.isEmpty()){
            return null;
        }
        return appUserList;
    }

    @RequestMapping(value = "/appuser/{appUserId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AppUserRequest getAppUser(@PathVariable("appUserId") String appUserId) {
        AppUser appUser = null;

        try {
            int userId = Integer.parseInt(appUserId);
            if (userId != 0) {
                appUser = appUserService.getOneAppUserByUserId(userId);
                if (appUser != null) {
                    AppUserRequest appUserRequest = new AppUserRequest();
                    appUserRequest.appUserId = appUser.getAppUserId();
                    appUserRequest.appUser = appUser;
                    appUserRequest.role = appUser.getRole();
                    appUserRequest.roles = (List<Role>) roleService.getAll();
                    return appUserRequest;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @RequestMapping(value = "/appuser", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public AppUserRequest addAppUser(@RequestBody AppUserRequest appUserRequest) {
        AppUser aU = appUserService.getOneAppUserByUsername(appUserRequest.appUser.getUsername());
        if (aU != null) {
            System.out.println("The AppUser "+appUserRequest.appUser.getUsername()+" is already existed.");
            return null;
        } else {
            appUserService.addAppUserRequest(appUserRequest);
            if (appUserRequest.appUser.getRole() != null) {
                roleService.addAppUserRequest(appUserRequest);
            }
            return appUserRequest;
        }
    }

    @RequestMapping(value = "/appuser/{appUserId}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public AppUserRequest updateAppUser(@RequestBody AppUserRequest appUserRequest) {
        AppUser aU = appUserService.getOneAppUserByUsername(appUserRequest.appUser.getUsername());
        if(aU != null) {
            appUserService.updateAppUserRequest(appUserRequest);
            if (appUserRequest.appUser.getRole() != null) {
                roleService.addAppUserRequest(appUserRequest);
            }
            System.out.println("The AppUser "+appUserRequest.appUser.getUsername()+"has been updated.");
            return appUserRequest;
        } else {
            System.out.println("The AppUser "+appUserRequest.appUser.getUsername()+" can not be found.");
            return null;
        }
    }

    @RequestMapping(value = "/appuser/{appUserId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public int deleteAppUser(@PathVariable("appUserId") String appUserId) {
        try {
            int uid = Integer.parseInt(appUserId);
            AppUser appUser = appUserService.getOneAppUserByUserId(uid);

            if (appUser != null) {
                appUserService.deleteAppUserById(uid);
                System.out.println("The AppUser "+appUser.getUsername()+" has been deleted.");
                return 1;
            } else {
                System.out.println("The AppUser "+appUser.getUsername()+" can not be found.");
                return 0;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

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
