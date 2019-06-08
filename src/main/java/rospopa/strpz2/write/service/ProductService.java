package rospopa.strpz2.write.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rospopa.strpz2.write.domain.Product;
import rospopa.strpz2.write.persistence.ProductRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public Collection<Product> getAll(Set<Long> ids) {
        var products = productRepository.findAllByIdIn(ids);
        checkArgument(products.size() == ids.size(), "Invalid collection of product ids: %s", ids);
        return products;
    }

    public List<Product> getAll() {
        return Lists.newArrayList(productRepository.findAll());
    }
}
