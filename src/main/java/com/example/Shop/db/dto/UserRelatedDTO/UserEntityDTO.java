package com.example.Shop.db.dto.UserRelatedDTO;

import com.example.Shop.db.entities.UserRelatedEntities.CartEntity;
import com.example.Shop.db.entities.UserRelatedEntities.OrderEntity;
import com.example.Shop.db.entities.UserRelatedEntities.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"userId", "userFirstname", "userLastname", "username", "userEmail", "userPhoneNumber"})
public class UserEntityDTO {
    @JsonProperty("userId")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15, message = "firstname too short(big)!(3<x<15)")
    @JsonProperty("userFirstname")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15, message = "lastname too short(big)!(3<x<15)")
    @JsonProperty("userLastname")
    private String lastName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15, message = "Username too short(big)!(3<x<15)")
    @JsonProperty("username")
    private String username;

    @JsonProperty("userDateJoined")
    @CreatedDate
    private Instant dateJoined;

    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15, message = "Username too short(big)!(3<x<15)")
    @JsonProperty("userEmail")
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 12, max = 13, message = "Correct phone number pls")
    @JsonProperty("userPhoneNumber")
    private String phoneNumber;
}