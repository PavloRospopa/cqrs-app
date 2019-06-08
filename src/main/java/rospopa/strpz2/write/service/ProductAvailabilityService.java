package rospopa.strpz2.write.service;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rospopa.strpz2.write.domain.OrderedProduct;
import rospopa.strpz2.write.domain.Product;
import rospopa.strpz2.write.domain.ProductAvailableQuantity;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class ProductAvailabilityService {
    private static final String PRODUCTS_AVAILABLE_QUANTITY_MAP_KEY = "productsAvailableQuantity";

    private final ProductService productService;
    private HashOperations<String, Long, ProductAvailableQuantity> hashOperations;

    public ProductAvailabilityService(ProductService productService, RedisTemplate<String, Object> redisTemplate) {
        this.productService = productService;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @PostConstruct
    private void warmUpCache() {
        var productsAvailableQuantity = extractAvailableQuantity(productService.getAll());
        hashOperations.putAll(PRODUCTS_AVAILABLE_QUANTITY_MAP_KEY, productsAvailableQuantity);
        log.info("Products available quantity cache was filled with data");
    }

    public boolean checkAvailability(Collection<OrderedProduct> orderedProducts) {
        var productIds = orderedProducts.stream().map(OrderedProduct::getProductId).collect(toSet());
        var productsAvailableQuantity = hashOperations.multiGet(PRODUCTS_AVAILABLE_QUANTITY_MAP_KEY, productIds)
                .stream()
                .filter(Objects::nonNull)
                .collect(toMap(ProductAvailableQuantity::getProductId, Function.identity()));

        var difference = Sets.difference(productIds, productsAvailableQuantity.keySet());
        checkArgument(difference.size() == 0, "Products with ids %s are not found", difference);

        for (var item : orderedProducts) {
            var availableQuantity = productsAvailableQuantity.get(item.getProductId()).getAvailableQuantity();
            if (item.getQuantity() > availableQuantity) {
                log.debug("Product[id={}] is not available in such quantity[{}]", item.getProductId(), item.getQuantity());
                return false;
            }
        }
        return true;
    }

    public void updateAvailableQuantity(Collection<Product> products) {
        var productsAvailableQuantity = extractAvailableQuantity(products);
        hashOperations.putAll(PRODUCTS_AVAILABLE_QUANTITY_MAP_KEY, productsAvailableQuantity);
        log.debug("Products available quantity cache was updated");
    }

    private Map<Long, ProductAvailableQuantity> extractAvailableQuantity(Collection<Product> products) {
        return products.stream().collect(toMap(Product::getId, ProductAvailableQuantity::new));
    }
}
