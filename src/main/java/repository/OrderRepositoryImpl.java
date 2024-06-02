package repository;

import base.exception.NotFoundException;
import base.repository.BaseRepositoryImpl;
import connection.SessionFactorySingleton;
import entity.Order;
import entity.enums.OrderCondition;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order,Integer>
        implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public List<Order> findByCustomerAndCondition(int id, OrderCondition orderCondition) throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Order> query = session.createQuery(" FROM Order o WHERE o.customer.id =:id AND o.orderCondition =:orderCondition",
                Order.class);
        query.setParameter("id",id);
        query.setParameter("orderCondition",orderCondition);
        List<Order> orders = query.list();
        session.close();
        return orders;
    }

    @Override
    public List<Order> allOrders() throws NotFoundException {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<Order> query = session.createQuery(" FROM Order o ",
                Order.class);
        List<Order> orders = query.list();
        session.close();
        return orders;
    }
}
