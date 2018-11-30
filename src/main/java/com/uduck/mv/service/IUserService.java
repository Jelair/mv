package com.uduck.mv.service;

import com.uduck.mv.entity.User;
import com.uduck.mv.entity.dto.UserDTO;

public interface IUserService {

    boolean insertUser(User user);

    User getUserByUsername(String username);

    UserDTO getUserDTOByUsername(String username);
}
