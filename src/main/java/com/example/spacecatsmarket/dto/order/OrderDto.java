package com.example.spacecatsmarket.dto.order;

import com.example.spacecatsmarket.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private UUID orderNumber;
    private double totalPrice;

    private Long customerId;
    private String customerName;

    private List<ProductDto> products;
}
