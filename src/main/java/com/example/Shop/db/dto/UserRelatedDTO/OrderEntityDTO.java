package com.example.Shop.db.dto.UserRelatedDTO;


import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"orderId", "orderDestination"})
public class OrderEntityDTO {

    @JsonProperty("orderId")
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("orderDestination")
    public String destination;

//    @NotNull
//    @NotBlank
//    @Size(min = 3, max = 15, message = "firstname too short(big)!(3<x<15)")
//    @JsonProperty("userFirstname")
//    private String firstName;
//
//    @NotNull
//    @NotBlank
//    @Size(min = 3, max = 15, message = "lastname too short(big)!(3<x<15)")
//    @JsonProperty("userLastname")
//    private String lastName;
//
//    @NotNull
//    @NotBlank
//    @Size(min = 3, max = 15, message = "Username too short(big)!(3<x<15)")
//    @JsonProperty("userEmail")
//    @Email
//    private String email;
//
//    @NotNull
//    @NotBlank
//    @Size(min = 12, max = 13, message = "Correct phone number pls")
//    @JsonProperty("userPhoneNumber")
//    private String phoneNumber;

}