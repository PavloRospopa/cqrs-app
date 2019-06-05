package rospopa.strpz2.read.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rospopa.strpz2.read.persistence.ReadProductRepository;
import rospopa.strpz2.read.web.dto.ProductDto;

import java.util.List;

import static rospopa.strpz2.read.web.controller.ProductController.ENDPOINT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ENDPOINT)
class ProductController {
    static final String ENDPOINT = "/products";

    private final ReadProductRepository readProductRepository;
    private final MapperFacade mapperFacade;

    @GetMapping
    List<ProductDto> getProducts() {
        log.info("Received request to get list of products");
        return mapperFacade.mapAsList(readProductRepository.findAll(), ProductDto.class);
    }
}
