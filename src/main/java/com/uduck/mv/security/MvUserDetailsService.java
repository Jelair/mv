package com.uduck.mv.security;

import com.uduck.mv.entity.Role;
import com.uduck.mv.entity.User;
import com.uduck.mv.service.IRoleService;
import com.uduck.mv.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MvUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = getAuthorities(user.getId());
        MvUserDetails mv = modelMapper.map(user, MvUserDetails.class);
        mv.setGrantedAuthorities(grantedAuthorities);
        new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
        return mv;
    }

    private List<GrantedAuthority> getAuthorities(Integer userId){
        List<Role> roles = roleService.getRolesByUserId(userId);
        if (roles == null || roles.isEmpty()){
            throw new DisabledException("用户非法");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return grantedAuthorities;
    }
}
