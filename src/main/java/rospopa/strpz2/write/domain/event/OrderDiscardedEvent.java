package rospopa.strpz2.write.domain.event;

import rospopa.strpz2.write.domain.Order;

public class OrderDiscardedEvent extends OrderEvent {

    public OrderDiscardedEvent(Order order) {
        super(order);
    }
}
