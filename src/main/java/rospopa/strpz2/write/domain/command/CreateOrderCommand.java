package rospopa.strpz2.write.domain.command;

import lombok.Data;
import rospopa.strpz2.write.domain.OrderedItem;

import java.util.List;

@Data
public class CreateOrderCommand {
    private List<OrderedItem> items;
}
