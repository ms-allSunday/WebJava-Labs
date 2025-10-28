package com.example.spacecatsmarket.dto.product;

import com.example.spacecatsmarket.dto.validation.CosmicWordCheck;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    @CosmicWordCheck
    String name;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 200, message = "Description must be less than 200 characters")
    String description;

    @NotNull(message = "Price must not be null")
    @Min(value = 0, message = "Price must be greater than 0")
    double price;

    @NotNull(message = "In stock must not be null")
    @Min(value = 0, message = "In stock must be 0 or greater")
    Integer inStock;
}
