package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepository;
import entity.Order;
import entity.enums.OrderCondition;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order,Integer> {
    List<Order> findByCustomerAndCondition(int id, OrderCondition orderCondition) throws NotFoundException;
    List<Order> allOrders() throws NotFoundException;
}
