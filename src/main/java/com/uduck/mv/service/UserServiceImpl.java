package com.uduck.mv.service;

import com.uduck.mv.constant.RoleConstant;
import com.uduck.mv.entity.Role;
import com.uduck.mv.entity.User;
import com.uduck.mv.entity.dto.UserDTO;
import com.uduck.mv.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public boolean insertUser(User user) {
        if (exist(user.getUsername())){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setLastUpdateTime(new Date());
        user.setNickname(user.getUsername());
        user.setAvatar("/img/defaultAvatar.jpeg");
        User save = userRepository.save(user);
        Role role = new Role();
        role.setName(RoleConstant.ROLE_USER);
        role.setUserId(save.getId());
        roleService.insertUserRole(role);
        return true;
    }

    /**
     * 用于登录验证的
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user;
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }


    private boolean exist(String username){
        User user = userRepository.findUserByUsername(username);
        return (user != null);
    }
}
