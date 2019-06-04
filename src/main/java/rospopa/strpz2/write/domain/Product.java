package rospopa.strpz2.write.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static com.google.common.base.Preconditions.checkArgument;

@Data
@Entity(name = "writeProduct")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
    @SequenceGenerator(name = "products_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private Long id;
    //private String name;
    //private String description;
    private int availableQuantity;
    //private double price;

    public void deductAvailableQuantity(int quantity) {
        checkArgument(quantity > 0, "Invalid quantity: must be positive number");
        var result = this.availableQuantity - quantity;
        checkArgument(result >= 0, "Invalid quantity: greater than available quantity of product");
        this.availableQuantity = result;
    }

    public void addAvailableQuantity(int quantity) {
        checkArgument(quantity > 0, "Invalid quantity: must be positive number");
        this.availableQuantity += quantity;
    }
}
