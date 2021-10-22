package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.repository.IAppUserRepository;
import com.employee.springbootemployeerestful.repository.IRoleRepository;
import com.employee.springbootemployeerestful.request.AppUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppUserServiceImpl implements IAppUserService{

    @Autowired
    private IAppUserRepository appUserRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Iterable<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser getOneAppUserByUserId(int userid) {
        return appUserRepository.findAppUsersByAppUserId(userid);
    }

    @Override
    public AppUser getOneAppUserByUsername(String username) {
        return appUserRepository.findAppUsersByUsername(username);
    }

    @Override
    public AppUser getOneAppUserByUsernameAndPassword(String username, String password) {
        return appUserRepository.findAppUsersByUsernameAndPassword(username, password);
    }

    @Override
    public AppUser getOneAppUserByEmail(String email) {
        return appUserRepository.findAppUsersByEmail(email);
    }

    @Override
    public List<AppUser> getAppUserListByRoleId(int roleid) {
        List<AppUser> appUserList = (List<AppUser>) getAll();
        List<AppUser> appUsersByRoleList = new ArrayList<AppUser>();

        for (AppUser appuser : appUserList) {
            if(appuser.getRole().getRoleId() == roleid) {
                appUsersByRoleList.add(appuser);
            }
        }

        return appUsersByRoleList;
    }

    @Override
    public List<AppUser> getAppUserListByRoleTitle(String roletitle) {
        List<AppUser> appUserList = (List<AppUser>) getAll();
        List<AppUser> appUsersByRoleList = new ArrayList<AppUser>();

        for (AppUser appuser : appUserList) {
            if(appuser.getRole().getRoleTitle() == roletitle) {
                appUsersByRoleList.add(appuser);
            }
        }

        return appUsersByRoleList;
    }

    @Override
    public int addAppUserByUsername(AppUser appuser) {
        if (!isAppUserExist(appuser.getUsername())) {
            appUserRepository.save(appuser);
            return 1;
        }
        else
            return 0;
    }

    @Override
    public int updateAppUserByUsername(AppUser appuser) {
        if (isAppUserExist(appuser.getUsername())) {
            appUserRepository.save(appuser);
            return 1;
        }
        else
            return 0;
    }

    @Override
    public int deleteAppUserById(int appuserid) {
        if (appUserRepository.findAppUsersByAppUserId(appuserid) != null) {
            appUserRepository.deleteById(appuserid);
            return 1;
        }
        else
            return 0;
    }

    @Override
    public int deleteAppUserByUsername(String username) {
        return 0;
    }

    @Override
    public boolean isAppUserExist(String username) {
        if (appUserRepository.findAppUsersByUsername(username) != null)
            return true;
        return false;
    }

    public AppUserRequest addAppUserRequest(AppUserRequest appUserRequest) {
        AppUser appUser = null;
        if (appUserRequest != null && appUserRequest.appUserId == 0) {
            appUser = appUserRepository.findAppUsersByUsername(appUserRequest.appUser.getUsername());
            if (appUser != null)
                return null;
            else {
                appUserRepository.save(appUserRequest.appUser);
                return appUserRequest;
            }
        }
        return null;
    }

    public AppUserRequest updateAppUserRequest(AppUserRequest appUserRequest) {
        AppUser appUser = null;
        appUser = appUserRepository.findAppUsersByAppUserId(appUserRequest.appUser.getAppUserId());
        if (appUserRequest != null && appUserRequest.appUserId != 0) {
            appUserRepository.save(appUserRequest.appUser);
            return appUserRequest;
        }
        return null;
    }
}
