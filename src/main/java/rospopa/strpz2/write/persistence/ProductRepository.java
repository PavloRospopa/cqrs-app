package rospopa.strpz2.write.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rospopa.strpz2.write.domain.Product;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByIdIn(Collection<Long> ids);
}
