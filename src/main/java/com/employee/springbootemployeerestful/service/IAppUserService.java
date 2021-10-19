package com.employee.springbootemployeerestful.service;

import com.employee.springbootemployeerestful.entity.AppUser;

import java.util.List;

public interface IAppUserService {
    Iterable<AppUser> getAll();

    AppUser getOneAppUserByUserId(int userid);

    AppUser getOneAppUserByUsername(String username);

    AppUser getOneAppUserByUsernameAndPassword(String username, String password);

    AppUser getOneAppUserByEmail(String email);

    List<AppUser> getAppUserListByRoleId(int roleid);

    List<AppUser> getAppUserListByRoleTitle(String roletitle);

    int addAppUserByUsername(AppUser appuser);

    int updateAppUserByUsername(AppUser appuser);

    int deleteAppUserById(int appuserid);

    int deleteAppUserByUsername(String username);

    boolean isAppUserExist(String username);
}
