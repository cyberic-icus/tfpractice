package com.example.Shop.entities.UserRelatedEntities;

//import com.example.Shop.entities.CartEntity;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.Set;
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class UserEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long id;
//    public String firstName;
//    public String lastName;
//    @CreatedDate public Instant dateJoined;
//    private String password;
//    public String email;
//    @OneToOne(cascade = CascadeType.ALL)
//    CartEntity cartEntity;
//    @OneToMany public Set<RoleEntity> roleEntities;
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
