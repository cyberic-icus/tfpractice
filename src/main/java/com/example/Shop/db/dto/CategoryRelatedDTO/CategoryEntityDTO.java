package com.example.Shop.db.dto.CategoryRelatedDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"categoryId", "categoryName", "categoryDescription"})
public class CategoryEntityDTO {

    @JsonProperty("categoryId")
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("categoryName")
    public String categoryName;

    @NotNull
    @NotBlank
    @JsonProperty("categoryDescription")
    public String categoryDescription;


}
