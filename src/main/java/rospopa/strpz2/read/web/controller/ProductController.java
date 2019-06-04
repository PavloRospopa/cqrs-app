package rospopa.strpz2.read.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rospopa.strpz2.read.persistence.ReadProductRepository;
import rospopa.strpz2.read.web.dto.ProductDto;

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
    Page<ProductDto> getProducts(@PageableDefault(sort = "availableQuantity", direction = Sort.Direction.DESC)
                                         Pageable pageable) {
        log.debug("Received request to get page of products. Pageable: {}", pageable);
        return readProductRepository.findAll(pageable).map(product -> mapperFacade.map(product, ProductDto.class));
    }
}
