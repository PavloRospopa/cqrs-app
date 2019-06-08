package rospopa.strpz2.write.domain.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rospopa.strpz2.write.service.OrderService;

import static rospopa.strpz2.write.configuration.KafkaConfiguration.COMPLETE_ORDER_COMMANDS_TOPIC;
import static rospopa.strpz2.write.configuration.KafkaConfiguration.CREATE_ORDER_COMMANDS_TOPIC;
import static rospopa.strpz2.write.configuration.KafkaConfiguration.DISCARD_ORDER_COMMANDS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderCommandListener {
    private final OrderService orderService;

    @KafkaListener(topics = CREATE_ORDER_COMMANDS_TOPIC)
    void processCreateOrderCommand(ConsumerRecord<String, CreateOrderCommand> record) {
        log.debug("Consumed record: key: {}, value: {}, timestamp: {}", record.key(), record.value(), record.timestamp());
        orderService.create(record.key(), record.value().getOrderedProducts());
    }

    @KafkaListener(topics = DISCARD_ORDER_COMMANDS_TOPIC)
    void processDiscardOrderCommand(ConsumerRecord<String, DiscardOrderCommand> record) {
        log.debug("Consumed record: key: {}, value: {}, timestamp: {}", record.key(), record.value(), record.timestamp());
        orderService.discard(record.value().getOrderId());
    }

    @KafkaListener(topics = COMPLETE_ORDER_COMMANDS_TOPIC)
    void processCompleteOrderCommand(ConsumerRecord<String, CompleteOrderCommand> record) {
        log.debug("Consumed record: key: {}, value: {}, timestamp: {}", record.key(), record.value(), record.timestamp());
        orderService.complete(record.value().getOrderId());
    }
}
