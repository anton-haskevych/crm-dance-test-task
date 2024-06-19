package com.example.demo.security.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(value = {"authorities", "uuid", "token"})
@ToString
public class JwtUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Builder.Default
    @JsonIgnore
    private String uuid = UUID.randomUUID().toString();

    @Getter
    @Column(name = "username", nullable = false, unique = true)
    @JsonProperty("username")
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "jwt_user_role", joinColumns = @JoinColumn(name = "jwt_user_id"))
    @Column(columnDefinition = "enum('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_OWNER')")
    private Set<Role> role = new TreeSet<>(Set.of(Role.ROLE_USER));
    @Enumerated(EnumType.STRING)
    @Column(name = "position", columnDefinition = "enum ('Owner', 'Receptionist' 'Teacher', 'Student');", nullable = false, updatable = false)
    private Position position;
    @DateTimeFormat
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate birthday;
    @Column
    @Builder.Default
    @JsonIgnore
    private boolean enabled = true;
    @CreationTimestamp
    @Column(updatable = false)
    Timestamp registered;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (var r : this.role) {
            var sga = new SimpleGrantedAuthority(r.name());
            authorities.add(sga);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
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
        return this.enabled;
    }


    public JwtUser(JwtUser jwtUser) {
        this.username = jwtUser.getUsername();
        this.firstName = jwtUser.getFirstName();
        this.lastName = jwtUser.getLastName();
        this.email = jwtUser.getEmail();
        this.password = jwtUser.getPassword();
        this.role = jwtUser.getRole();
        this.position = jwtUser.getPosition();
        this.birthday = jwtUser.getBirthday();
        this.enabled = jwtUser.enabled;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public JwtUser(Long id) {
        this.id = id;
    }

}
