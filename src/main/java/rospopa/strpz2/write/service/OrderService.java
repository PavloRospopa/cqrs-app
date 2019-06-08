package rospopa.strpz2.write.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import rospopa.strpz2.write.domain.Order;
import rospopa.strpz2.write.domain.OrderedProduct;
import rospopa.strpz2.write.domain.event.OrderCreatedEvent;
import rospopa.strpz2.write.domain.event.OrderDiscardedEvent;
import rospopa.strpz2.write.persistence.OrderRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ApplicationEventPublisher eventPublisher;

    public Order create(String orderUuid, Collection<OrderedProduct> orderedProducts) {
        var order = new Order();
        order.setUuid(orderUuid);
        order.setStatus(Order.Status.CREATED);
        order = orderRepository.save(order);

        var productsQuantityMap = orderedProducts.stream()
                .collect(toMap(OrderedProduct::getProductId, OrderedProduct::getQuantity));
        var products = productService.getAll(productsQuantityMap.keySet());

        for (var product : products) {
            var quantity = productsQuantityMap.get(product.getId());
            product.deductAvailableQuantity(quantity);
            order.addItem(product, quantity);
        }
        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }

    public Order discard(String orderUuid) {
        var order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new EntityNotFoundException(format("Order with uuid '%s' is not found", orderUuid)));
        checkState(order.getStatus() == Order.Status.CREATED, "Invalid status of order '%s'", orderUuid);
        for (var item : order.getItems()) {
            var product = item.getProduct();
            product.addAvailableQuantity(item.getQuantity());
        }
        order.setStatus(Order.Status.DISCARDED);
        eventPublisher.publishEvent(new OrderDiscardedEvent(order));
        return order;
    }

    public Order complete(String orderUuid) {
        var order = orderRepository.findByUuid(orderUuid)
                .orElseThrow(() -> new EntityNotFoundException(format("Order with uuid '%s' is not found", orderUuid)));
        checkState(order.getStatus() == Order.Status.CREATED, "Invalid status of order '%s'", orderUuid);
        order.setStatus(Order.Status.COMPLETED);
        return order;
    }
}
