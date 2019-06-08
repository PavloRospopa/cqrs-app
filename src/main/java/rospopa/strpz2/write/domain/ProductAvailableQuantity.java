package rospopa.strpz2.write.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductAvailableQuantity implements Serializable {
    private long productId;
    private int availableQuantity;

    public ProductAvailableQuantity(Product product) {
        this.productId = product.getId();
        this.availableQuantity = product.getAvailableQuantity();
    }
}
