package org.wzx.mycasserver.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.wzx.mycasserver.entity.UserInfo;

import java.util.Collection;


/**
 * @description:
 * @author: 鱼头
 * @time: 2021-10-8 21:41
 */
@Data
@Slf4j
@Builder
@AllArgsConstructor
public class MyUserDetails implements UserDetails {
    private UserInfo userInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.userInfo.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userInfo.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.userInfo.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.userInfo.getIsEnabled();
    }
}
