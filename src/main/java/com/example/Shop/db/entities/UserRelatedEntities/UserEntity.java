package com.example.Shop.db.entities.UserRelatedEntities;


import com.example.Shop.db.entities.OrderRelatedEntites.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;

    @CreatedDate
    private Instant dateJoined;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();

    @OneToMany(mappedBy = "orderUserEntity", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<OrderEntity> orders = new HashSet<>();

//    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private List<OrderEntity> orders_history = new ArrayList<>();

//    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    private CartEntity cartEntity = new CartEntity();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}