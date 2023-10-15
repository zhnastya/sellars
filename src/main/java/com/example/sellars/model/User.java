package com.example.sellars.model;

import com.example.sellars.model.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    @NotBlank(message = "email не может быть пустым или равняться нулю")
    @Email(message = "email не валидный")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name")
    @NotBlank(message = "имя не может быть пустым")
    @Size(min=2, message = "Не меньше 2 знаков")
    private String name;

    @Column(name = "active")
    private boolean active;

    @Column(name = "password", length = 1000)
    @NotBlank(message = "пароль не может быть пустым")
    @Size(min=10, message = "Не меньше 10 знаков")
    private String password;

    @Column(name = "dateOfCreated")
    private LocalDateTime dateOfCreated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "prod_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> likesProd;


    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }


    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return active;
    }
}
