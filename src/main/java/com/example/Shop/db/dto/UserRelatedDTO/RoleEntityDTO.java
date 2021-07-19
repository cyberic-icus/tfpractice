package com.example.Shop.db.dto.UserRelatedDTO;

import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


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

