package rospopa.strpz2.write.web.controller;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rospopa.strpz2.write.domain.command.CompleteOrderCommand;
import rospopa.strpz2.write.domain.command.CreateOrderCommand;
import rospopa.strpz2.write.domain.command.DiscardOrderCommand;
import rospopa.strpz2.write.domain.command.OrderCommandHandler;
import rospopa.strpz2.write.web.dto.CreateOrderRequestDto;
import rospopa.strpz2.write.web.dto.IdDto;

import javax.validation.Valid;

import static rospopa.strpz2.write.web.controller.OrderController.ENDPOINT;

@RequiredArgsConstructor
@RestController
@RequestMapping(ENDPOINT)
class OrderController {
    static final String ENDPOINT = "/orders";

    private final MapperFacade mapperFacade;
    private final OrderCommandHandler commandHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    IdDto create(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {
        var command = mapperFacade.map(createOrderRequestDto, CreateOrderCommand.class);
        var orderId = commandHandler.handle(command);
        return IdDto.of(orderId);
    }

    @PostMapping("/{id}/discard")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void discard(@PathVariable String id) {
        var command = new DiscardOrderCommand(id);
        commandHandler.handle(command);
    }

    @PostMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void complete(@PathVariable String id) {
        var command = new CompleteOrderCommand(id);
        commandHandler.handle(command);
    }
}
