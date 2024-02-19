package com.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.models.Role;
import com.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public CustomUserDetails(){ }
    public CustomUserDetails(User user){
//        this.user = user;
        this.password = user.getHashedPassword();
        this.username = user.getEmail();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.userId = user.getId();
        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for(Role role: user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
        return authorities;
    }

    @Override
    public String getPassword() {
//        return user.getHashedPassword();
        return password;
    }


    @Override
    public String getUsername() {
//        return user.getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
