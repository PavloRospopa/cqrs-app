package rospopa.strpz2.write.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    public static final String CREATE_ORDER_COMMANDS_TOPIC = "createOrderCommandsTopic";
    public static final String DISCARD_ORDER_COMMANDS_TOPIC = "discardOrderCommandsTopic";
    public static final String COMPLETE_ORDER_COMMANDS_TOPIC = "completeOrderCommandsTopic";

    @Bean
    NewTopic createOrderCommandsTopic() {
        return new NewTopic(CREATE_ORDER_COMMANDS_TOPIC, 1, (short) 1);
    }

    @Bean
    NewTopic discardOrderCommandsTopic() {
        return new NewTopic(DISCARD_ORDER_COMMANDS_TOPIC, 1, (short) 1);
    }


    @Bean
    NewTopic completeOrderCommandsTopic() {
        return new NewTopic(COMPLETE_ORDER_COMMANDS_TOPIC, 1, (short) 1);
    }
}
