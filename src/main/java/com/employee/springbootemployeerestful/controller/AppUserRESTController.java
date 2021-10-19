package com.employee.springbootemployeerestful.controller;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.entity.Role;
import com.employee.springbootemployeerestful.request.AppUserRequest;
import com.employee.springbootemployeerestful.service.AppUserServiceImpl;
import com.employee.springbootemployeerestful.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserRESTController {

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    private RoleServiceImpl roleService;

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
}
