package com.uduck.mv.service;

import com.uduck.mv.entity.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getRolesByUserId(Integer userId);

    boolean insertUserRole(Role role);

    boolean updateUserRole(Role role);
}
