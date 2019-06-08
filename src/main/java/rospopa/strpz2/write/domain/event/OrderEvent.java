package rospopa.strpz2.write.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import rospopa.strpz2.write.domain.Order;

@Data
@AllArgsConstructor
public abstract class OrderEvent {
    protected Order order;
}
