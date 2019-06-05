package rospopa.strpz2.read.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rospopa.strpz2.read.domain.Product;

@Repository
public interface ReadProductRepository extends CrudRepository<Product, Long> {
}
