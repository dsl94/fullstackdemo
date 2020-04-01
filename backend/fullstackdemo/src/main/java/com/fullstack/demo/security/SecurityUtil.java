package com.fullstack.demo.security;

import com.fullstack.demo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static UserDetails getLoggedUserDetails() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails)
            return (UserDetails) principal;

        return null;
    }

    public static JwtUser getLoggedJwtUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            JwtUser jwtUser = (JwtUser) userDetails;

            return jwtUser;
        }

        return null;
    }

    public static User getLoggedDbUser() {
        return getLoggedJwtUser().getDbUser();
    }

}

