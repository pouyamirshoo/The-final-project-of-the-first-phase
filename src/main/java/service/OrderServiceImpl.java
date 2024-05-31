package service;

import base.exception.NotFoundException;
import base.exception.ReturnMethodException;
import base.service.BaseServiceImpl;
import entity.Order;
import entity.enums.OrderCondition;
import org.hibernate.SessionFactory;
import repository.OrderRepository;

import java.util.List;

public class OrderServiceImpl extends BaseServiceImpl<Order, Integer, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Order> findByCustomerAndCondition(int id, OrderCondition orderCondition) {
        List<Order> orders;
        try {
            orders = repository.findByCustomerAndCondition(id, orderCondition);
        } catch (NotFoundException e) {
            throw new ReturnMethodException("");
        }
        return orders;
    }

    @Override
    public List<Order> allOrders() {
        List<Order> orders;
        try {
            orders = repository.allOrders();
        } catch (NotFoundException e) {
            throw new ReturnMethodException("");
        }
        return orders;
    }
}
