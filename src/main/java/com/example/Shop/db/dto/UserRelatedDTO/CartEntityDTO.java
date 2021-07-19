package com.example.Shop.db.dto.UserRelatedDTO;

import com.example.Shop.db.entities.CategoryRelatedEntities.ProductEntity;
import com.example.Shop.db.entities.UserRelatedEntities.UserEntity;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"cart_id"})
public class CartEntityDTO {

    @JsonProperty("cart_id")
    public Long id;

}