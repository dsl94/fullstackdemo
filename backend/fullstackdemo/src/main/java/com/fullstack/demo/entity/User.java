package com.fullstack.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private boolean isActive;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    public User() {
        this.isActive = true;
    }
}
