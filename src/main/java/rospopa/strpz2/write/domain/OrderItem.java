package rospopa.strpz2.write.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(of = {"id", "quantity"})
@ToString(of = {"id", "quantity"})
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;
    @MapsId("order_id")
    @ManyToOne
    private Order order;
    @MapsId("product_id")
    @ManyToOne
    private Product product;
    private int quantity;

    public OrderItem(Order order, Product product, int quantity) {
        this.id = new OrderItemId(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
