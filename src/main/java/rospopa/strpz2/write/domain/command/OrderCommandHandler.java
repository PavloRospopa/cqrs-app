package rospopa.strpz2.write.domain.command;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import rospopa.strpz2.write.configuration.KafkaConfiguration;
import rospopa.strpz2.write.service.ProductAvailabilityService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderCommandHandler {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ProductAvailabilityService productAvailabilityService;

    public String handle(CreateOrderCommand command) {
        if (!productAvailabilityService.checkAvailability(command.getOrderedProducts())) {
            throw new IllegalArgumentException("Cannot satisfy request to create order. " +
                    "Some ordered products are unavailable in such quantity");
        }
        var id = UUID.randomUUID().toString();
        kafkaTemplate.send(KafkaConfiguration.CREATE_ORDER_COMMANDS_TOPIC, id, command);
        return id;
    }

    public void handle(DiscardOrderCommand command) {
        kafkaTemplate.send(KafkaConfiguration.DISCARD_ORDER_COMMANDS_TOPIC, command.getOrderId(), command);
    }

    public void handle(CompleteOrderCommand command) {
        kafkaTemplate.send(KafkaConfiguration.COMPLETE_ORDER_COMMANDS_TOPIC, command.getOrderId(), command);
    }
}
