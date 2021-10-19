package com.employee.springbootemployeerestful.repository;

import com.employee.springbootemployeerestful.entity.AppUser;
import com.employee.springbootemployeerestful.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAppUserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findAppUsersByAppUserId(int appuserid);
    AppUser findAppUsersByUsername(String username);
    AppUser findAppUsersByUsernameAndPassword(String username, String password);
    AppUser findAppUsersByEmail(String email);
    List<AppUser> findAppUsersByUsernameIsNotNull();
    List<AppUser> findAppUsersByRole(Role role);
    boolean existsAppUserByUsername(String username);
    boolean existsAppUserByUsernameAndPassword(String username, String password);
    int deleteAppUserByAppUserId(String appuserid);
    int deleteAppUserByUsername(String username);

}
