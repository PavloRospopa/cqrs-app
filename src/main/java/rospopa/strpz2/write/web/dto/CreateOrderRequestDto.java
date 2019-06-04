package rospopa.strpz2.write.web.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class CreateOrderRequestDto {
    @NotEmpty
    private List<@Valid OrderItemDto> items;

    @Data
    public static class OrderItemDto {
        private long productId;
        @Positive
        private int quantity;
    }
}
