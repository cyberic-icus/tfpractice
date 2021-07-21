package com.example.Shop.db.dto.UserRelatedDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntityDTO {
    @JsonProperty("roleId")
    private Long id;

    @NotNull
    @NotBlank
    @JsonProperty("roleName")
    private String name;

}

