package com.example.Shop.db.dto.UserRelatedDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"password", "userDateJoined", "username"})
@JsonPropertyOrder({"userId", "userFirstname", "userLastname", "username", "userEmail", "userPhoneNumber"})
public class UserEntityDTO {
    @JsonProperty(value = "userId", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    @NotBlank
    @JsonProperty("userFirstname")
    private String firstName;

    @NotNull
    @NotBlank
    @JsonProperty("userLastname")
    private String lastName;

    @NotNull
    @NotBlank
    @JsonProperty("username")
    @JsonIgnore
    private String username;

    @JsonProperty(value = "userDateJoined", access = JsonProperty.Access.READ_ONLY)
    @CreatedDate
    private Instant dateJoined;


    @NotNull
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private String password;

    @NotNull
    @NotBlank
    @JsonProperty("userEmail")
    private String email;

    @NotNull
    @NotBlank
    @JsonProperty("userPhoneNumber")
    private String phoneNumber;
}