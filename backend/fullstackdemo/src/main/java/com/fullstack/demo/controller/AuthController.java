package com.fullstack.demo.controller;

import com.fullstack.demo.controller.dto.RegisterRequest;
import com.fullstack.demo.entity.Role;
import com.fullstack.demo.entity.User;
import com.fullstack.demo.repository.RoleRepository;
import com.fullstack.demo.repository.UserRepository;
import com.fullstack.demo.security.JwtTokenUtil;
import com.fullstack.demo.security.RolesConstants;
import com.fullstack.demo.security.dto.JwtAuthenticationDto;
import com.fullstack.demo.security.dto.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<JwtResponse> createAuthenticationToken(
            @RequestBody JwtAuthenticationDto jwtAuthenticationDto)
            throws Exception {

        // Checking username|email and password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jwtAuthenticationDto.getUsername(), jwtAuthenticationDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        Date expiration = jwtTokenUtil.getExpirationDateFromToken(token);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        return ResponseEntity.ok(new JwtResponse(token, dateFormat.format(expiration), userDetails.getUsername(), roles));

    }

    @GetMapping("/current")
    public ResponseEntity<UserDetails> getCurrent() throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authenticatedUserName = authentication.getName();
        if(authenticatedUserName.equals("anonymousUser"))
            // TODO - Throw better exception
            throw new Exception(authenticatedUserName);
        else
            return ResponseEntity.ok((UserDetails)authentication.getPrincipal());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {

        Role role = roleRepository.findByRoleIgnoreCase(RolesConstants.ROLE_USER.name());
        User user = new User();
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}

