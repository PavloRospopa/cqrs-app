package rospopa.strpz2.write.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import rospopa.strpz2.write.domain.OrderItem;
import rospopa.strpz2.write.service.ProductAvailabilityService;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final ProductAvailabilityService productAvailabilityService;

    @TransactionalEventListener
    public void process(OrderEvent event) {
        log.debug("Received event: {}", event);
        var products = event.getOrder().getItems().stream().map(OrderItem::getProduct).collect(toList());
        productAvailabilityService.updateAvailableQuantity(products);
    }
}
