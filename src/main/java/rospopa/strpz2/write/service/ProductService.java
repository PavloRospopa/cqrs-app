package rospopa.strpz2.write.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rospopa.strpz2.write.domain.Product;
import rospopa.strpz2.write.persistence.ProductRepository;

import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Collection<Product> getAll(Set<Long> ids) {
        var products = productRepository.findAllByIdIn(ids);
        checkArgument(products.size() == ids.size(), "Invalid collection of product ids: %s", ids);
        return products;
    }
}
