package rospopa.strpz2.write.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rospopa.strpz2.write.domain.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByUuid(String uuid);
}
