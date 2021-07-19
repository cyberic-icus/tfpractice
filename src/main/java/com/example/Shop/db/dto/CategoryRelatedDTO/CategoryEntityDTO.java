package com.example.Shop.db.dto.CategoryRelatedDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(min = 3, max = 15, message = "Too big or too small category name!")
    @JsonProperty("categoryName")
    public String categoryName;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100, message = "Too big or too small category description!")
    @JsonProperty("categoryDescription")
    public String categoryDescription;


}
