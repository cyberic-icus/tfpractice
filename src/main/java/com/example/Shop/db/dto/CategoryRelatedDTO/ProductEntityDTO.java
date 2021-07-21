package com.example.Shop.db.dto.CategoryRelatedDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"productCreatedOn"})
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({"productId", "productName", "productDescription", "productPrice", "productCreatedOn"})
public class ProductEntityDTO {

    @JsonProperty(value = "productId", access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    @NotBlank
    @JsonProperty("productName")
    public String productName;

    @NotNull
    @NotBlank
    @JsonProperty("productDescription")
    public String productDescription;

    @JsonProperty("productPrice")
    public Long price;

    @JsonProperty(value = "productCreatedOn", access = JsonProperty.Access.READ_ONLY)
    @CreatedDate
    public Instant createdDate;

    @NotNull
    @NotBlank
    @JsonProperty("productImageUrl")
    public String imageUrl;

}
