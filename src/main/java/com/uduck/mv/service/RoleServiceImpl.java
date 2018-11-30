package com.uduck.mv.service;

import com.uduck.mv.entity.Role;
import com.uduck.mv.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        List<Role> roles = roleRepository.findRolesByUserId(userId);
        return roles;
    }

    @Override
    public boolean insertUserRole(Role role) {
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean updateUserRole(Role role) {
        roleRepository.save(role);
        return true;
    }
}
