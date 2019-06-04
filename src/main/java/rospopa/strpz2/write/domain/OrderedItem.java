package rospopa.strpz2.write.domain;

import lombok.Data;

@Data
public class OrderedItem {
    private long productId;
    private int quantity;
}
