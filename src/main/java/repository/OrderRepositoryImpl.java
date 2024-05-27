package repository;

import base.repository.BaseRepositoryImpl;
import entity.Order;
import org.hibernate.SessionFactory;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order,Integer>
        implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Order> getEntityClass() {
        return null;
    }
}
