package rospopa.strpz2.write.domain.command;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rospopa.strpz2.write.configuration.KafkaConfiguration;

import java.util.UUID;

import static rospopa.strpz2.write.configuration.KafkaConfiguration.CREATE_ORDER_COMMANDS_TOPIC;

@Service
@RequiredArgsConstructor
public class OrderCommandHandler {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String handle(CreateOrderCommand command) {
        var id = UUID.randomUUID().toString();
        kafkaTemplate.send(CREATE_ORDER_COMMANDS_TOPIC, id, command);
        return id;
    }

    public void handle(DiscardOrderCommand command) {
        kafkaTemplate.send(KafkaConfiguration.DISCARD_ORDER_COMMANDS_TOPIC, command.getOrderId(), command);
    }

    public void handle(CompleteOrderCommand command) {
        kafkaTemplate.send(KafkaConfiguration.COMPLETE_ORDER_COMMANDS_TOPIC, command.getOrderId(), command);
    }
}
