package rospopa.strpz2.write.domain.command;

import lombok.Data;
import rospopa.strpz2.write.domain.OrderedProduct;

import java.util.List;

@Data
public class CreateOrderCommand {
    private List<OrderedProduct> orderedProducts;
}
