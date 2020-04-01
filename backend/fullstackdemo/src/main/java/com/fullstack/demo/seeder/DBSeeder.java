package com.fullstack.demo.seeder;

import com.fullstack.demo.entity.Role;
import com.fullstack.demo.repository.RoleRepository;
import com.fullstack.demo.repository.UserRepository;
import com.fullstack.demo.security.RolesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DBSeeder {

    private Logger logger = LoggerFactory.getLogger(DBSeeder.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener
    public void seed(ContextRefreshedEvent event){
        seedRolesTable();
//        seedUsersTable();
    }

    private void seedRolesTable() {
        if (roleRepository.findAll().size() == 0) {
            logger.info("Seeding roles");
            Role admin = new Role();
            admin.setName("Admin");
            admin.setRole(RolesConstants.ROLE_ADMIN.name());
            roleRepository.save(admin);

            Role user = new Role();
            user.setName("User");
            user.setRole(RolesConstants.ROLE_USER.name());
            roleRepository.save(user);
        }
    }

//    private void seedUsersTable() {
//        if (userRepository.findAll().size() == 0) {
//            logger.info("Seeding users");
//            Role superAdminRole = roleRepository.findByRoleIgnoreCase(RolesConstants.ROLE_SUPERADMIN.name());
//            Role adminRole = roleRepository.findByRoleIgnoreCase(RolesConstants.ROLE_ADMIN.name());
//            Role userRole = roleRepository.findByRoleIgnoreCase(RolesConstants.ROLE_USER.name());
//
//            User superadmin = new User();
//            superadmin.setFirstName("Super");
//            superadmin.setLastName("Admin");
//            superadmin.setEmail("superadmin@mail.com");
//            superadmin.setUsername("superadmin");
//            superadmin.setPassword(passwordEncoder.encode("superadmin"));
//            superadmin.setActive(true);
//            superadmin.setRoles(Collections.singleton(superAdminRole));
//            userRepository.save(superadmin);
//
//            User admin = new User();
//            admin.setFirstName("Admin");
//            admin.setLastName("Admin");
//            admin.setEmail("admin@mail.com");
//            admin.setUsername("admin");
//            admin.setRoles(Collections.singleton(adminRole));
//            admin.setActive(true);
//            admin.setPassword(passwordEncoder.encode("admin"));
//            userRepository.save(admin);
//
//            User user = new User();
//            user.setFirstName("User");
//            user.setLastName("User");
//            user.setEmail("user@mail.com");
//            user.setUsername("user");
//            user.setRoles(Collections.singleton(userRole));
//            user.setActive(true);
//            user.setPassword(passwordEncoder.encode("user"));
//            userRepository.save(user);
//        }
//    }
}