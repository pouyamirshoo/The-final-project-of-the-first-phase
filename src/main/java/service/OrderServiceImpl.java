package service;

import base.service.BaseServiceImpl;
import entity.Order;
import org.hibernate.SessionFactory;
import repository.OrderRepository;

public class OrderServiceImpl extends BaseServiceImpl<Order,Integer, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
