package rospopa.strpz2.read.web.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int availableQuantity;
    private double price;
}
