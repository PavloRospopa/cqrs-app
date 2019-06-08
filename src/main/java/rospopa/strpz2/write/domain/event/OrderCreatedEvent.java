package rospopa.strpz2.write.domain.event;

import rospopa.strpz2.write.domain.Order;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order) {
        super(order);
    }
}
