package rospopa.strpz2.read.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "readProduct")
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private int availableQuantity;
    private double price;
}
